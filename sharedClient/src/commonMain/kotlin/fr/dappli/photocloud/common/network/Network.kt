package fr.dappli.photocloud.common.network

import fr.dappli.photocloud.common.db.CacheKey
import fr.dappli.photocloud.common.db.Database
import fr.dappli.photocloud.common.vo.Token
import fr.dappli.photocloud.common.vo.User
import fr.dappli.sharedclient.Platform
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class Network(private val database: Database) {

    private var hostAddress: String = Platform.debugHost
    private var bearerTokens: BearerTokens? = null

    val host: String
        get() = hostAddress

    init {
        bearerTokens = database.getFromCache(CacheKey.ACCESS_TOKEN.name)?.let { accessToken ->
            println("access token from db: $accessToken")
            val refreshToken = database.getFromCache(CacheKey.REFRESH_TOKEN.name) ?: ""
            BearerTokens(accessToken, refreshToken)
        }
    }

    val isLoggedIn: Boolean
        get() = bearerTokens != null

    private val nonAuthClient = HttpClient(Platform.engineFactory) {
        install(ContentNegotiation) {
            json()
        }
        logging()
        defaultRequest {
            url {
                protocol = URLProtocol.HTTP
                host = hostAddress
                port = 9090 // TODO for debug
            }
        }
    }

    val authClient = HttpClient(Platform.engineFactory) {
        install(ContentNegotiation) {
            json()
        }
        logging()
        install(Auth) {
            bearer {
                loadTokens {
                    bearerTokens
                }
                refreshTokens {
                    bearerTokens?.let {
                        bearerTokens = updateTokens(it)
                        bearerTokens
                    }
                }
            }
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTP
                host = hostAddress
                port = 9090 // TODO for debug
            }
        }
    }

    suspend fun login(name: String, password: String, hostAddress: String): Boolean {
        return try {
            this.hostAddress = hostAddress
            database.insertOrIgnoreToCacheWithUpdate(CacheKey.HOST_ADDRESS.name, hostAddress)

            val response = nonAuthClient.post {
                url {
                    host = hostAddress
                    encodedPath = "login"
                }
                contentType(ContentType.Application.Json)
                setBody(User(name, password))
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    val token = response.body<Token>()
                    database.insertToCache(CacheKey.ACCESS_TOKEN.name, token.accessToken)
                    database.insertToCache(CacheKey.REFRESH_TOKEN.name, token.refreshToken)
                    bearerTokens = BearerTokens(token.accessToken, token.refreshToken)
                    true
                }
                else -> {
                    println(response)
                    false
                }
            }

        } catch (e: ResponseException) {
            println(e)
            false
        }
    }

    suspend fun logout() {
        database.clearCache(CacheKey.ACCESS_TOKEN.name)
        database.clearCache(CacheKey.REFRESH_TOKEN.name)
        bearerTokens = null
        // clear a host adress from database and set a default one in variable
        database.clearCache(CacheKey.HOST_ADDRESS.name)
        hostAddress = Platform.debugHost
        // TODO we should implement logout on server side to clear a token
    }

    private suspend fun updateTokens(oldTokens: BearerTokens): BearerTokens {
        val newToken = nonAuthClient.post {
            url {
                encodedPath = "refresh"
            }
            contentType(ContentType.Application.Json)
            setBody(Token(oldTokens.accessToken, oldTokens.refreshToken))
        }.body<Token>()
        println("new token: $newToken")
        database.updateCache(CacheKey.ACCESS_TOKEN.name, newToken.accessToken)
        database.updateCache(CacheKey.REFRESH_TOKEN.name, newToken.refreshToken)
        return BearerTokens(newToken.accessToken, newToken.refreshToken)
    }

    private fun HttpClientConfig<HttpClientEngineConfig>.logging() {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
    }
}

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

    private var bearerTokens: BearerTokens? = null

    init {
        bearerTokens = database.getFromCache(CacheKey.ACCESS_TOKEN.name)?.let { accessToken ->
            println("access token from db: $accessToken")
            BearerTokens(accessToken, "")
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
                host = Platform.debugHost
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
                    TODO("implement refresh endpoint with oldTokens?.refreshToken")
                }
            }
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTP
                host = Platform.debugHost
                port = 9090 // TODO for debug
            }
        }
    }

    suspend fun login(name: String, password: String): Boolean {
        return try {
            val response = nonAuthClient.post {
                url {
                    encodedPath = "login"
                }
                contentType(ContentType.Application.Json)
                setBody(User(name, password))
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    val token = response.body<Token>()
                    database.insertToCache(CacheKey.ACCESS_TOKEN.name, token.accessToken)
                    bearerTokens = BearerTokens(token.accessToken, "")
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

    private fun HttpClientConfig<HttpClientEngineConfig>.logging() {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
    }
}

package fr.dappli.photocloud.common.network

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
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Network {

    private var bearerTokens: BearerTokens? = null

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

    private fun HttpClientConfig<HttpClientEngineConfig>.logging() {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
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
}

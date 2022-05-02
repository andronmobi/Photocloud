package fr.dappli.photocloud.common.network

import fr.dappli.photocloud.common.vo.Token
import fr.dappli.photocloud.common.vo.User
import fr.dappli.sharedclient.Platform
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Network {

    private val nonAuthClient = HttpClient(Platform.engineFactory) {
        install(ContentNegotiation) {
            json()
        }
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
        install(Auth) {
            bearer {
                loadTokens {
                    getToken()
                }
                refreshTokens {
                    getToken()
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

    private suspend fun getToken(): BearerTokens {
        val response = nonAuthClient.post {
            url {
                encodedPath = "login"
            }
            contentType(ContentType.Application.Json)
            setBody(User("foo", "bar")) // TODO
        }
        val token = response.body<Token>()
        return BearerTokens(token.accessToken, "")
    }
}

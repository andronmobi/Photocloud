package fr.dappli.photocloud.common.network

import fr.dappli.photocloud.common.getDebugHost
import fr.dappli.photocloud.common.vo.Token
import fr.dappli.photocloud.common.vo.User
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class Network {

    private val baseJson by lazy {
        Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }

    private val nonAuthClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        defaultRequest {
            url {
                protocol = URLProtocol.HTTP
                host = getDebugHost()
                port = 9090 // TODO for debug
            }
        }
    }

    val authClient = HttpClient {

        install(JsonFeature) {
            serializer = KotlinxSerializer(baseJson)
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
                host = getDebugHost()
                port = 9090 // TODO for debug
            }
        }
    }

    private suspend fun getToken(): BearerTokens {
        val token = nonAuthClient.post<Token> {
            url {
                encodedPath = "login"
            }
            contentType(ContentType.Application.Json)
            body = User("foo", "bar") // TODO
        }
        return BearerTokens(token.accessToken, "")
    }
}
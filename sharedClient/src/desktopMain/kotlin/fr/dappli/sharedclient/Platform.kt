package fr.dappli.sharedclient

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

actual object Platform {
    actual val platform: String = "Desktop"
    actual val debugHost: String = "localhost"
    actual val engineFactory: HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
}
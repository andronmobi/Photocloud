package fr.dappli.sharedclient

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.EmptyCoroutineContext

actual object Platform {
    actual val platform: String = "Desktop"
    actual val debugHost: String = "localhost"
    actual val engineFactory: HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
    actual val mainCoroutineScope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)
}

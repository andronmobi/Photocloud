package fr.dappli.sharedclient

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual object Platform {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
    actual val engineFactory: HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
    actual val uiDispatcher: CoroutineContext = Dispatchers.Main
    actual val defaultDispatcher: CoroutineContext = Dispatchers.Default
}

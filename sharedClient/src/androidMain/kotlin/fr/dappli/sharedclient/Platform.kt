package fr.dappli.sharedclient

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

actual object Platform {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
    actual val debugHost: String = "192.168.1.22"
    actual val engineFactory: HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
    actual val mainCoroutineScope: CoroutineScope = MainScope()
}

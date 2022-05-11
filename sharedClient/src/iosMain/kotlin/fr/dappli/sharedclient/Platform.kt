package fr.dappli.sharedclient

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.UIKit.UIDevice
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.freeze

actual object Platform {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    actual val debugHost: String = "192.168.1.2"
    actual val engineFactory: HttpClientEngineFactory<HttpClientEngineConfig> = Darwin
    actual val uiDispatcher: CoroutineContext = MainDispatcher
}

@ThreadLocal
private object MainDispatcher: CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue()) {
            try {
                block.run().freeze()
            } catch (err: Throwable) {
                throw err
            }
        }
    }
}

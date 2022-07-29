package fr.dappli.sharedclient

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.UIKit.UIDevice
import platform.darwin.DISPATCH_QUEUE_PRIORITY_DEFAULT
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_global_queue
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.freeze

actual object Platform {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    actual val engineFactory: HttpClientEngineFactory<HttpClientEngineConfig> = Darwin
    actual val uiDispatcher: CoroutineContext = MainDispatcher
    actual val defaultDispatcher: CoroutineContext = MainDispatcher
}

/**
 * https://medium.com/google-developer-experts/kotlin-native-multithreading-with-coroutines-373663bf5a09
 */
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

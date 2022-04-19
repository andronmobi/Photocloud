package fr.dappli.sharedclient

import io.ktor.client.engine.*
import io.ktor.client.engine.ios.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import platform.UIKit.UIDevice

actual object Platform {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    actual val debugHost: String = "192.168.1.2"
    actual val engineFactory: HttpClientEngineFactory<HttpClientEngineConfig> = Ios
    actual val mainCoroutineScope: CoroutineScope = MainScope()
}

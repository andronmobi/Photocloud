package fr.dappli.sharedclient

import io.ktor.client.engine.*
import io.ktor.client.engine.ios.*
import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    actual val debugHost: String = "192.168.1.2"
    actual val engineFactory: HttpClientEngineFactory<HttpClientEngineConfig> = Ios
}
package fr.dappli.sharedclient

actual class Platform actual constructor() {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
    actual val debugHost: String = "192.168.1.2"
}
package fr.dappli.photocloud.common

import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.graphics.ImageBitmap
import java.io.InputStream

actual fun getPlatformName(): String {
    return "Desktop"
}

actual fun getDebugHost(): String {
    return "localhost"
}

actual fun loadBitmap(inputStream: InputStream): ImageBitmap {
    return loadImageBitmap(inputStream)
}
package fr.dappli.photocloud.common

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.BufferedInputStream
import java.io.InputStream

actual fun getPlatformName(): String {
    return "Android"
}

actual fun getDebugHost(): String {
    return "192.168.1.2"
}

actual fun loadBitmap(inputStream: InputStream): ImageBitmap {
    val bitmap = BitmapFactory.decodeStream(BufferedInputStream(inputStream))
    return bitmap.asImageBitmap()
}
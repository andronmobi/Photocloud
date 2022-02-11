package fr.dappli.photocloud.common

import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
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

@Composable
actual fun iconDirPainter(): Painter = painterResource(R.drawable.ic_dir)

@Composable
actual fun iconPhotoPainter(): Painter = painterResource(R.drawable.ic_photo)
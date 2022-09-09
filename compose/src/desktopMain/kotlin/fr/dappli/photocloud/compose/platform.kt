package fr.dappli.photocloud.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import java.io.InputStream

actual val isDesktop: Boolean = true

actual fun loadBitmap(inputStream: InputStream): ImageBitmap {
    return loadImageBitmap(inputStream)
}

@Composable
actual fun iconDirPainter(): Painter = painterResource("ic_dir.svg")
@Composable
actual fun iconPhotoPainter(): Painter = painterResource("ic_photo.svg")
@Composable
actual fun iconCloudPainter(): Painter = painterResource("ic_cloud.svg")

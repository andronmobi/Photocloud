package fr.dappli.photocloud.compose

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import java.io.InputStream

expect val isDesktop: Boolean

expect fun loadBitmap(inputStream: InputStream): ImageBitmap

expect fun iconDirPainter(): Painter
expect fun iconPhotoPainter(): Painter
expect fun iconCloudPainter(): Painter

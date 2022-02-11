package fr.dappli.photocloud.common

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import java.io.InputStream

expect fun getPlatformName(): String

expect fun getDebugHost(): String

expect fun loadBitmap(inputStream: InputStream): ImageBitmap

expect fun iconDirPainter(): Painter

expect fun iconPhotoPainter(): Painter
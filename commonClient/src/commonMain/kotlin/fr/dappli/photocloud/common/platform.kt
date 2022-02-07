package fr.dappli.photocloud.common

import androidx.compose.ui.graphics.ImageBitmap
import java.io.InputStream

expect fun getPlatformName(): String

expect fun getDebugHost(): String

expect fun loadBitmap(inputStream: InputStream): ImageBitmap
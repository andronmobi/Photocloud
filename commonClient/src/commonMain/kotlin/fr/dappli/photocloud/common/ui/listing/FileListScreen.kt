package fr.dappli.photocloud.common.ui.listing

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.dappli.photocloud.common.iconDirPainter
import fr.dappli.photocloud.common.iconPhotoPainter
import fr.dappli.photocloud.common.loadBitmap
import fr.dappli.photocloud.common.network.Network
import fr.dappli.photocloud.common.vo.Config
import fr.dappli.photocloud.common.vo.Dir
import fr.dappli.photocloud.common.vo.PCFile
import fr.dappli.photocloud.common.vo.Photo
import fr.dappli.sharedclient.Platform.platform
import io.ktor.client.request.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.*
import java.io.ByteArrayInputStream
import java.util.*
import kotlin.collections.HashMap
import androidx.compose.ui.graphics.Color as ComposeColor

@Composable
fun FileListScreen() {

    var text by remember { mutableStateOf("Get files") }
    var dir by remember { mutableStateOf("Not connected") }
    var files by remember { mutableStateOf(emptyList<PCFile>()) }
    var isEnabled by remember { mutableStateOf(true) }

    Column {
        Button(
            onClick = {
                MainScope().launch {
                    val config: Config = Network.authClient.get {
                        url {
                            encodedPath = "config"
                        }
                    }
                    dir = "${config.rootDir}"
                    text = "Get files from $platform"

                    files = Network.authClient.get {
                        url {
                            encodedPath = "file/${config.rootDir.id}"
                        }
                    }

                    isEnabled = false
                }
            },
            enabled = isEnabled
        ) {
            Text(text)
        }
        Text(dir)
        Spacer(Modifier.size(16.dp))
        Grillage(files)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Grillage(files: List<PCFile>) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(116.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(files) { item ->
            Card(
                backgroundColor = ComposeColor.LightGray,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                val fileName = item.id.decodeFileId().toName()
                when (item) {
                    is Dir -> {
                        Box {
                            Image(
                                painter = iconDirPainter(),
                                contentDescription = null,
                                modifier = Modifier.size(100.dp).align(Alignment.Center)
                            )
                            Text(
                                fontSize = 8.sp,
                                text = fileName,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    is Photo -> {
                        AsyncImage(
                            photo = item,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            }
        }
    }
}

// TODO improve me
private var photoCache = HashMap<String, ImageBitmap?>()

@Composable
private fun AsyncImage(
    photo: Photo,
    modifier: Modifier = Modifier
) {
    val bitmap: ImageBitmap? by produceState<ImageBitmap?>(null) {
        if (value == null) {
            value = withContext(Dispatchers.IO) {
                try {
                    if (photoCache.contains(photo.id)) {
                        photoCache[photo.id]
                    } else {
                        downloadImageBitmap(photo.id).also {
                            photoCache[photo.id] = it
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }

    if (bitmap == null) {
        Image(
            painter = iconPhotoPainter(),
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        bitmap?.let {
            Image(
                bitmap = it,
                contentDescription = null,
                modifier = modifier,
                contentScale = ContentScale.Crop
            )
        }
    }
}

private suspend fun downloadImageBitmap(photoId: String): ImageBitmap {
    val byteArray = Network.authClient.get<ByteArray> {
        url {
            encodedPath = "file/${photoId}/download"
        }
    }
    val stream = ByteArrayInputStream(byteArray)
    return loadBitmap(stream)
}

private fun String.decodeFileId() = String(Base64.getUrlDecoder().decode(this))

private fun String.toName() = substringAfter("photocloud:///")

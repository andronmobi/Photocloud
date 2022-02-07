package fr.dappli.photocloud.common.ui.listing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import fr.dappli.photocloud.common.getPlatformName
import fr.dappli.photocloud.common.loadBitmap
import fr.dappli.photocloud.common.network.Network
import fr.dappli.photocloud.vo.Config
import fr.dappli.photocloud.vo.Dir
import fr.dappli.photocloud.vo.PCFile
import fr.dappli.photocloud.vo.Photo
import io.ktor.client.request.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.util.*

@Composable
fun FileListScreen() {

    val network = Network()
    var text by remember { mutableStateOf("Get files") }
    var dir by remember { mutableStateOf("Not connected") }
    var files by remember { mutableStateOf(emptyList<PCFile>()) }
    var isEnabled by remember { mutableStateOf(true) }

    Column {
        Button(
            onClick = {
                MainScope().launch {
                    val config: Config = network.authClient.get {
                        url {
                            encodedPath = "config"
                        }
                    }
                    dir = "${config.rootDir}"
                    text = "Get files from ${getPlatformName()}"

                    files = network.authClient.get {
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
        FileList(network, files)
    }
}

@Composable
fun FileList(network: Network, files: List<PCFile>) {
    LazyColumn {
        items(
            items = files,
            itemContent = { item ->
                Row {
                    val fileLocation = item.id.decodeFileId()
                    when (item) {
                        is Dir -> {
                            // TODO dir placeholder Image()
                            Text("Dir: $fileLocation")
                        }
                        is Photo -> {
                            AsyncImage(network, item)
                            Text("Photo: $fileLocation")
                        }
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
            }
        )
    }
}

@Composable
private fun AsyncImage(network: Network, photo: Photo) {
    var img: ImageBitmap? by remember { mutableStateOf(null) }
    val bitmap: ImageBitmap? by produceState(img) {
        if (value == null) {
            value = withContext(Dispatchers.IO) {
                try {
                    val byteArray = network.authClient.get<ByteArray> {
                        url {
                            encodedPath = "file/${photo.id}/download"
                        }
                    }
                    val stream = ByteArrayInputStream(byteArray)
                    loadBitmap(stream).also { img = it }
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }

    bitmap?.let {
        Image(
            bitmap = it,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}

private fun String.decodeFileId() = String(Base64.getUrlDecoder().decode(this))
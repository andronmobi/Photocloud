package fr.dappli.photocloud.common.ui.listing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.dappli.photocloud.common.getPlatformName
import fr.dappli.photocloud.common.network.Network
import fr.dappli.photocloud.vo.Config
import fr.dappli.photocloud.vo.Dir
import fr.dappli.photocloud.vo.PCFile
import io.ktor.client.request.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

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
        FileList(files)
    }
}

@Composable
fun FileList(files: List<PCFile>) {
    LazyColumn {
        items(
            items = files,
            itemContent = { item ->
                val type = if (item is Dir) "Dir: " else "Image:"
                Text("$type${item.id}")
            }
        )
    }
}

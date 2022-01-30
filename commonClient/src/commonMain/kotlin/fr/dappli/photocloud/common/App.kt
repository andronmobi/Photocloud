package fr.dappli.photocloud.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.dappli.photocloud.common.network.Network
import fr.dappli.photocloud.vo.Config
import fr.dappli.photocloud.vo.Dir
import fr.dappli.photocloud.vo.PCFile
import io.ktor.client.request.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun App() {
    val network = Network()

    var text by remember { mutableStateOf("Get files") }
    var dir by remember { mutableStateOf("Not connected") }
    var fileIds by remember { mutableStateOf("") }
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

                    val files: List<PCFile> = network.authClient.get {
                        url {
                            encodedPath = "file/${config.rootDir.id}"
                        }
                    }
                    fileIds = files.joinToString {
                        val type = if (it is Dir) "Dir: " else "Image:"
                        "$type${it.id}"
                    }
                    isEnabled = false
                }
            },
            enabled = isEnabled
        ) {
            Text(text)
        }
        Text(dir)
        Spacer(Modifier.size(4.dp))
        Text(fileIds)
    }
}

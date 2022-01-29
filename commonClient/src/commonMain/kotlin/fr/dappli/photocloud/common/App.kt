package fr.dappli.photocloud.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import fr.dappli.photocloud.common.network.Network
import fr.dappli.photocloud.vo.Config
import io.ktor.client.request.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun App() {
    val network = Network()

    var text by remember { mutableStateOf("Connect") }
    var dir by remember { mutableStateOf("Not connected") }
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
                    text = "Connected from ${getPlatformName()}"
                    isEnabled = false
                    dir = "${config.rootDir}"
                }
            },
            enabled = isEnabled
        ) {
            Text(text)
        }
        Text(dir)
    }
}

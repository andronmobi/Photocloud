package fr.dappli.common

import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import fr.dappli.common.network.Network
import io.ktor.client.request.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun App() {
    val network = Network()

    var text by remember { mutableStateOf("Hello, World!") }

    Button(onClick = {
        text = "Hello, ${getPlatformName()}"
        MainScope().launch {
            val s: String = network.client.get {
                url {
                    encodedPath = "/ocs/v2.php/cloud/user"
                }
                parameter("format", "json")
            }
            println("request: $s")
        }
    }) {
        Text(text)
    }
}

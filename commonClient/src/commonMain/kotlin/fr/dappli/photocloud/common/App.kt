package fr.dappli.photocloud.common

import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import fr.dappli.photocloud.common.network.Network
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
            val s: String = network.authClient.get {
                url {
                    encodedPath = "hello"
                }
            }
            println("response: $s")
        }
    }) {
        Text(text)
    }
}

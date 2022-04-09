package fr.dappli.photocloud.android

import fr.dappli.photocloud.common.root.RootComponent
import fr.dappli.photocloud.common.App
import com.arkivanov.decompose.defaultComponentContext
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootComponent = RootComponent(defaultComponentContext())

        setContent {
            MaterialTheme {
                App(rootComponent)
            }
        }
    }
}

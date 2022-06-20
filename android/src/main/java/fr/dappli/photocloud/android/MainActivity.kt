package fr.dappli.photocloud.android

import fr.dappli.photocloud.common.root.RootComponent
import fr.dappli.photocloud.compose.App
import com.arkivanov.decompose.defaultComponentContext
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import fr.dappli.photocloud.common.db.DatabaseDriverFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootComponent = RootComponent(
            defaultComponentContext(),
            DatabaseDriverFactory(this)
        )

        setContent {
            MaterialTheme {
                App(rootComponent)
            }
        }
    }
}

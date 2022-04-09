import fr.dappli.photocloud.common.App
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import fr.dappli.photocloud.common.root.RootComponent

fun main() = application {

    val lifecycle = LifecycleRegistry()
    val ctx = DefaultComponentContext(lifecycle = lifecycle)
    val rootComponent = RootComponent(ctx)

    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            App(rootComponent)
        }
    }
}

package fr.dappli.photocloud.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import fr.dappli.photocloud.common.list.PhotoListComponent

private val lifecycle = LifecycleRegistry()
private val ctx = DefaultComponentContext(lifecycle = lifecycle)
private val photoListComponent = PhotoListComponent(ctx)

@Preview
@Composable
fun AppPreview() {
    App(photoListComponent)
}

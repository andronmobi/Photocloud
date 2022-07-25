package fr.dappli.photocloud.compose.root

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import fr.dappli.photocloud.common.root.Root
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import fr.dappli.photocloud.compose.home.HomeUi
import fr.dappli.photocloud.compose.login.LoginUi
import fr.dappli.photocloud.compose.splash.SplashUi

@Composable
fun RootUi(root: Root) {
    val routerState by root.routerState.subscribeAsState()
    Surface(modifier = Modifier.fillMaxHeight()) {
        Children(
            routerState = routerState
        ) { child ->
            when (val child = child.instance) {
                is Root.Child.LoginChild -> LoginUi(child.component)
                is Root.Child.SplashChild -> SplashUi()
                is Root.Child.HomeChild -> HomeUi(child.component)
            }
        }
    }
}

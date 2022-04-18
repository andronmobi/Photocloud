package fr.dappli.photocloud.compose.root

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.dappli.photocloud.common.root.Root
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
fun RootUi(root: Root) {
    Box {
        println("andrei box scope")
        val routerState by root.routerState.subscribeAsState()

        val activeChild = routerState.activeChild.instance
        if (activeChild is Root.Child.ListChild) {
            Text("data ok", modifier = Modifier.align(Alignment.Center))
        } else {
            Text("loading...", modifier = Modifier.align(Alignment.Center))
        }
    }
}

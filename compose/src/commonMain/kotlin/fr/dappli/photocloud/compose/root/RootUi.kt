package fr.dappli.photocloud.compose.root

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.dappli.photocloud.common.root.Root
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import fr.dappli.photocloud.compose.list.PhotoListUI

@Composable
fun RootUi(root: Root) {
    val routerState by root.routerState.subscribeAsState()
    Box {
        val activeChild = routerState.activeChild.instance
        if (activeChild is Root.Child.ListChild) {
            PhotoListUI(activeChild.photoList)
        } else {
            Text("loading...", modifier = Modifier.align(Alignment.Center))
        }
    }
}

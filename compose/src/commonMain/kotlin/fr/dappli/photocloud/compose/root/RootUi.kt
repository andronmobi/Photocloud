package fr.dappli.photocloud.compose.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import fr.dappli.photocloud.common.root.Root
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.crossfade
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import fr.dappli.photocloud.compose.list.PhotoListUI

@Composable
fun RootUi(root: Root) {
    val routerState by root.routerState.subscribeAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp
            ) {
                if (routerState.backStack.size > 1) {
                    val photoList = (routerState.activeChild.instance as Root.Child.ListChild).photoList
                    IconButton(onClick = { photoList.onBackClicked() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    Text("Dir name")
                } else {
                    Text("Photocloud", modifier = Modifier.padding(start = 16.dp))
                }
            }
        }
    ) {
        val activeChild = routerState.activeChild.instance
        if (activeChild is Root.Child.ListChild) {
            Children(
                routerState = routerState
            ) {
                PhotoListUI(activeChild.photoList)
            }
        } else {
            Text("loading...")
        }
    }
}

package fr.dappli.photocloud.compose.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import fr.dappli.photocloud.common.root.Root
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import fr.dappli.photocloud.compose.isDesktop
import fr.dappli.photocloud.compose.list.PhotoListUI

@Composable
fun RootUi(root: Root) {
    val routerState by root.routerState.subscribeAsState()
    val navIdState = remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp
            ) {
                when (navIdState.value) {
                    Nav.HOME.ordinal -> {
                        if (routerState.backStack.isNotEmpty()) {
                            val photoList =
                                (routerState.activeChild.instance as Root.Child.ListChild).photoList
                            IconButton(onClick = { photoList.onBackClicked() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                            Text(photoList.currentDir.name)
                        } else {
                            Text("Photocloud", modifier = Modifier.padding(start = 16.dp))
                        }
                    }
                    Nav.SETTINGS.ordinal -> {
                        Text("Settings", modifier = Modifier.padding(start = 16.dp))
                    }
                }
            }
        },
        bottomBar = {
            if (!isDesktop) {
                BottomBar(navIdState)
            }
        }
    ) {
        val activeChild = routerState.activeChild.instance
        if (activeChild is Root.Child.ListChild) {
            when (navIdState.value) {
                Nav.HOME.ordinal -> {
                    Children(
                        routerState = routerState
                    ) {
                        PhotoListUI(activeChild.photoList)
                    }
                }
                Nav.SETTINGS.ordinal -> {
                    Text("TODO")
                }
            }
        } else {
            Text("loading...")
        }
    }
}

@Composable
fun BottomBar(navIdState: MutableState<Int>) {
    BottomNavigation(elevation = 10.dp) {
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Home, "")
            },
            label = { Text(text = "Home") },
            selected = (navIdState.value == Nav.HOME.ordinal),
            onClick = {
                navIdState.value = Nav.HOME.ordinal
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Settings, "")
            },
            label = { Text(text = "Settings") },
            selected = (navIdState.value == Nav.SETTINGS.ordinal),
            onClick = {
                navIdState.value = Nav.SETTINGS.ordinal
            }
        )
    }
}

private enum class Nav {
    HOME, SETTINGS
}

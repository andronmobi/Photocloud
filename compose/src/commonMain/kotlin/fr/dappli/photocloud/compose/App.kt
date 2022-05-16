package fr.dappli.photocloud.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.dappli.photocloud.common.root.Root
import fr.dappli.photocloud.compose.root.RootUi

@Composable
fun App(root: Root) {
    if (isDesktop) {
        AppNavRail {
            RootUi(root)
        }
    } else {
        RootUi(root)
    }
}

@Composable
fun AppNavRail(
    content: @Composable () -> Unit
) {
    Row {
        NavigationRail(modifier = Modifier.fillMaxHeight()) {
            NavigationRailItem(
                selected = true,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Home"
                    )
                },
                onClick = {
                    // TODO
                }
            )
        }
        content()
    }
}

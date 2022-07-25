package fr.dappli.photocloud.compose.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import fr.dappli.photocloud.common.home.Home
import fr.dappli.photocloud.compose.isDesktop
import fr.dappli.photocloud.compose.list.PhotoListUI

@Composable
fun HomeUi(home: Home) {
    val routerState by home.bottomNavRouterState.subscribeAsState()
    val currentChild = routerState.activeChild.instance
    if (isDesktop) {
        Row {
            AppNavRail(currentChild, home)
            Children(
                routerState = routerState,
                modifier = Modifier.weight(weight = 1F),
            ) {
                Content(it.instance, home)
            }
        }
    } else {
        Column {
            Children(
                routerState = routerState,
                modifier = Modifier.weight(weight = 1F),
            ) {
                Content(it.instance, home)
            }
            BottomBar(currentChild, home)
        }
    }
}

@Composable
private fun Content(child: Home.Child, home: Home) {
    when (child) {
        is Home.Child.HomeChild -> HomeContent(home)
        Home.Child.SettingsChild -> Text("Settings")
    }
}

@Composable
private fun HomeContent(home: Home) {
    Column {
        Children(
            routerState = home.homeRouterState,
            modifier = Modifier.weight(weight = 1F),
        ) {
            PhotoListUI(it.instance)
        }
    }
}

@Composable
private fun BottomBar(child: Home.Child, home: Home) {
    BottomNavigation(elevation = 10.dp) {
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Home, "")
            },
            label = { Text(text = "Home") },
            selected = (child == Home.Child.HomeChild),
            onClick = home::onTabHomeClick
        )
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Settings, "")
            },
            label = { Text(text = "Settings") },
            selected = (child == Home.Child.SettingsChild),
            onClick = home::onTabSettingsClick
        )
    }
}

@Composable
private fun AppNavRail(child: Home.Child, home: Home) {
    NavigationRail(modifier = Modifier.fillMaxHeight()) {
        NavigationRailItem(
            selected = (child == Home.Child.HomeChild),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home"
                )
            },
            onClick = home::onTabHomeClick
        )
        NavigationRailItem(
            selected = (child == Home.Child.SettingsChild),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings"
                )
            },
            onClick = home::onTabSettingsClick
        )
    }
}

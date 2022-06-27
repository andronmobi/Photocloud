package fr.dappli.photocloud.compose.home

import androidx.compose.foundation.layout.Column
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
import fr.dappli.photocloud.common.home.model.Screen

@Composable
fun HomeUi(home: Home) {
    val routerState by home.routerState.subscribeAsState()
    val currentScreen = routerState.activeChild.instance
    Column {
        Children(
            routerState = routerState,
            modifier = Modifier.weight(weight = 1F),
        ) {
            Content(it.instance)
        }
        BottomBar(home, currentScreen)
    }
}

@Composable
private fun Content(screen: Screen) {
    when (screen) {
        Screen.PhotoListScreen -> Text("PhotoList")
        Screen.SettingsScreen -> Text("Settings")
    }
}

@Composable
private fun BottomBar(home: Home, screen: Screen) {
    BottomNavigation(elevation = 10.dp) {
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Home, "")
            },
            label = { Text(text = "Home") },
            selected = (screen == Screen.PhotoListScreen),
            onClick = home::onTabHomeClick
        )
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Settings, "")
            },
            label = { Text(text = "Settings") },
            selected = (screen == Screen.SettingsScreen),
            onClick = home::onTabSettingsClick
        )
    }
}

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

@Composable
fun HomeUi(home: Home) {
    val routerState by home.routerState.subscribeAsState()
    val currentChild = routerState.activeChild.instance
    Column {
        Children(
            routerState = routerState,
            modifier = Modifier.weight(weight = 1F),
        ) {
            Content(it.instance)
        }
        BottomBar(home, currentChild)
    }
}

@Composable
private fun Content(child: Home.Child) {
    when (child) {
        Home.Child.PhotoListChild -> Text("PhotoList")
        Home.Child.SettingsChild -> Text("Settings")
    }
}

@Composable
private fun BottomBar(home: Home, child: Home.Child) {
    BottomNavigation(elevation = 10.dp) {
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Home, "")
            },
            label = { Text(text = "Home") },
            selected = (child == Home.Child.PhotoListChild),
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

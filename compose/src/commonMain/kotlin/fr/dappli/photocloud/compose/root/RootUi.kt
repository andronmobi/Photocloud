package fr.dappli.photocloud.compose.root

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import fr.dappli.photocloud.common.root.Root
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import fr.dappli.photocloud.compose.home.HomeUi
import fr.dappli.photocloud.compose.login.LoginUi
import fr.dappli.photocloud.compose.splash.SplashUi

@Composable
fun RootUi(root: Root) {
    val routerState by root.routerState.subscribeAsState()
    Column {
        Children(
            routerState = routerState
        ) { child ->
            when (val child = child.instance) {
                is Root.Child.LoginChild -> LoginUi(child.component)
                is Root.Child.SplashChild -> SplashUi()
                is Root.Child.HomeChild -> HomeUi(child.component)
            }
        }
        // TODO add bottom navigation
    }
}

//@Composable
//fun RootUi(root: Root) {
//    val routerState by root.routerState.subscribeAsState()
//    val navIdState = remember { mutableStateOf(0) }
//    if (isDesktop) {
//        Row {
//            AppNavRail(navIdState)
//            AppScaffold(routerState, navIdState)
//        }
//    } else {
//        AppScaffold(routerState, navIdState)
//    }
//}
//
//@Composable
//private fun AppScaffold(routerState: RouterState<*, Root.Child>, navIdState: MutableState<Int>) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                backgroundColor = Color.White,
//                elevation = 0.dp
//            ) {
//                when (navIdState.value) {
//                    Nav.HOME.ordinal -> {
//                        if (routerState.backStack.isNotEmpty()) {
//                            val photoList =
//                                (routerState.activeChild.instance as Root.Child.ListChild).photoList
//                            IconButton(onClick = { photoList.onBackClicked() }) {
//                                Icon(
//                                    imageVector = Icons.Filled.ArrowBack,
//                                    contentDescription = "Back"
//                                )
//                            }
//                            Text(photoList.currentDir.name)
//                        } else {
//                            Text("Photocloud", modifier = Modifier.padding(start = 16.dp))
//                        }
//                    }
//                    Nav.SETTINGS.ordinal -> {
//                        Text("Settings", modifier = Modifier.padding(start = 16.dp))
//                    }
//                }
//            }
//        },
//        bottomBar = {
//            if (!isDesktop) {
//                BottomBar(navIdState)
//            }
//        }
//    ) {
//        Content(routerState, navIdState)
//    }
//}
//
//@Composable
//private fun Content(routerState: RouterState<*, Root.Child>, navIdState: State<Int>) {
//    val activeChild = routerState.activeChild.instance
//    if (activeChild is Root.Child.ListChild) {
//        when (navIdState.value) {
//            Nav.HOME.ordinal -> {
//                Children(routerState = routerState) {
//                    PhotoListUI(activeChild.photoList)
//                }
//            }
//            Nav.SETTINGS.ordinal -> {
//                Text("TODO")
//            }
//        }
//    } else {
//        Text("loading...")
//    }
//}
//
//@Composable
//private fun BottomBar(navIdState: MutableState<Int>) {
//    BottomNavigation(elevation = 10.dp) {
//        BottomNavigationItem(
//            icon = {
//                Icon(imageVector = Icons.Default.Home, "")
//            },
//            label = { Text(text = "Home") },
//            selected = (navIdState.value == Nav.HOME.ordinal),
//            onClick = {
//                navIdState.value = Nav.HOME.ordinal
//            }
//        )
//        BottomNavigationItem(
//            icon = {
//                Icon(imageVector = Icons.Default.Settings, "")
//            },
//            label = { Text(text = "Settings") },
//            selected = (navIdState.value == Nav.SETTINGS.ordinal),
//            onClick = {
//                navIdState.value = Nav.SETTINGS.ordinal
//            }
//        )
//    }
//}
//
//@Composable
//private fun AppNavRail(navIdState: MutableState<Int>) {
//    NavigationRail(modifier = Modifier.fillMaxHeight()) {
//        NavigationRailItem(
//            selected = (navIdState.value == Nav.HOME.ordinal),
//            icon = {
//                Icon(
//                    imageVector = Icons.Filled.Home,
//                    contentDescription = "Home"
//                )
//            },
//            onClick = {
//                navIdState.value = Nav.HOME.ordinal
//            }
//        )
//        NavigationRailItem(
//            selected = (navIdState.value == Nav.SETTINGS.ordinal),
//            icon = {
//                Icon(
//                    imageVector = Icons.Filled.Settings,
//                    contentDescription = "Settings"
//                )
//            },
//            onClick = {
//                navIdState.value = Nav.SETTINGS.ordinal
//            }
//        )
//    }
//}
//
//private enum class Nav {
//    HOME, SETTINGS
//}

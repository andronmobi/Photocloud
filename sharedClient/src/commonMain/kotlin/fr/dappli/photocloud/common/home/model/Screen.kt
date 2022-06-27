package fr.dappli.photocloud.common.home.model

sealed class Screen {
    //class PhotoListScreen(val component: PhotoList) : Screen()
    object PhotoListScreen : Screen()
    object SettingsScreen : Screen()
}

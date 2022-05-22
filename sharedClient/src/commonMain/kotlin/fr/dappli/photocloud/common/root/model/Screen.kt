package fr.dappli.photocloud.common.root.model

import fr.dappli.photocloud.common.login.Login

sealed class Screen {
    class LoginScreen(val component: Login) : Screen()
    object SplashScreen : Screen()
    object HomeScreen : Screen()
}

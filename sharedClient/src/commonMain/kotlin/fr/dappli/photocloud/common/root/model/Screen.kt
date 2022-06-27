package fr.dappli.photocloud.common.root.model

import fr.dappli.photocloud.common.home.Home
import fr.dappli.photocloud.common.login.Login
import fr.dappli.photocloud.common.splash.Splash

sealed class Screen {
    class LoginScreen(val component: Login) : Screen()
    class SplashScreen(val component: Splash) : Screen()
    class HomeScreen(val component: Home) : Screen()
}

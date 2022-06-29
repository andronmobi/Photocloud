package fr.dappli.photocloud.common.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.home.Home
import fr.dappli.photocloud.common.login.Login
import fr.dappli.photocloud.common.splash.Splash

interface Root {
    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        class LoginChild(val component: Login) : Child()
        class SplashChild(val component: Splash) : Child()
        class HomeChild(val component: Home) : Child()
    }
}

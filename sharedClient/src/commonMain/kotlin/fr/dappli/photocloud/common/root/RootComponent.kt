package fr.dappli.photocloud.common.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.*
import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.login.LoginComponent
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.photocloud.common.root.model.Screen
import fr.dappli.photocloud.common.root.model.ScreenConfiguration
import fr.dappli.photocloud.common.root.model.ScreenConfiguration.SplashConfiguration
import fr.dappli.photocloud.common.root.model.ScreenConfiguration.HomeConfiguration
import fr.dappli.photocloud.common.root.model.ScreenConfiguration.LoginConfiguration
import fr.dappli.photocloud.common.splash.SplashComponent

class RootComponent(
    componentContext: ComponentContext
) : Root, ComponentContext by componentContext {

    private val photocloudLoader = PhotocloudLoader() // it could be injected to components

    private val router: Router<ScreenConfiguration, Screen> = router(
        initialConfiguration = LoginConfiguration,
        handleBackButton = true, // Pop the back stack on back button press
        childFactory = ::createScreen
    )

    override val routerState: Value<RouterState<*, Screen>> = router.state

    private fun createScreen(config: ScreenConfiguration, context: ComponentContext): Screen {
        return when (config) {
            is LoginConfiguration -> Screen.LoginScreen(
                LoginComponent(context, photocloudLoader) {
                    router.replaceCurrent(SplashConfiguration)
                }
            )
            is SplashConfiguration -> Screen.SplashScreen(
                SplashComponent(context, photocloudLoader) {
                    router.replaceCurrent(HomeConfiguration)
                }
            )
            is HomeConfiguration -> Screen.HomeScreen
        }
    }

}

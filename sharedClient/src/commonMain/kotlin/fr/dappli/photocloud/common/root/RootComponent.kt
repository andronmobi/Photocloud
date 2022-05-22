package fr.dappli.photocloud.common.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.*
import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.login.LoginComponent
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.photocloud.common.root.model.Screen
import fr.dappli.photocloud.common.root.model.ScreenConfiguration

class RootComponent(
    componentContext: ComponentContext
) : Root, ComponentContext by componentContext {

    private val photocloudLoader = PhotocloudLoader()

    private val router: Router<ScreenConfiguration, Screen> = router(
        initialConfiguration = ScreenConfiguration.LoginConfiguration,
        handleBackButton = true, // Pop the back stack on back button press
        childFactory = ::createScreen
    )

    override val routerState: Value<RouterState<*, Screen>> = router.state

    private fun createScreen(config: ScreenConfiguration, context: ComponentContext): Screen {
        return when (config) {
            is ScreenConfiguration.LoginConfiguration -> Screen.LoginScreen(LoginComponent(context))
            is ScreenConfiguration.SplashConfiguration -> Screen.SplashScreen
            is ScreenConfiguration.HomeConfiguration -> Screen.HomeScreen
        }
    }

}

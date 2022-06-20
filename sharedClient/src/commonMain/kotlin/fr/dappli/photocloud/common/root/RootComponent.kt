package fr.dappli.photocloud.common.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.*
import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.db.Database
import fr.dappli.photocloud.common.db.DatabaseDriverFactory
import fr.dappli.photocloud.common.login.LoginComponent
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.photocloud.common.root.model.Screen
import fr.dappli.photocloud.common.root.model.ScreenConfiguration
import fr.dappli.photocloud.common.root.model.ScreenConfiguration.SplashConfiguration
import fr.dappli.photocloud.common.root.model.ScreenConfiguration.HomeConfiguration
import fr.dappli.photocloud.common.root.model.ScreenConfiguration.LoginConfiguration
import fr.dappli.photocloud.common.splash.SplashComponent

class RootComponent(
    componentContext: ComponentContext,
    databaseDriverFactory: DatabaseDriverFactory
) : Root, ComponentContext by componentContext {

    // it could be injected to components
    private val database = Database(databaseDriverFactory)
    private val photocloudLoader = PhotocloudLoader(database)

    private val router: Router<ScreenConfiguration, Screen> = router(
        initialConfiguration = LoginConfiguration,
        handleBackButton = true, // Pop the back stack on back button press
        childFactory = ::createScreen
    )

    override val routerState: Value<RouterState<*, Screen>> = router.state

    private fun createScreen(config: ScreenConfiguration, context: ComponentContext): Screen {
        val newConfig = if (config is LoginConfiguration && photocloudLoader.isLoggedIn) {
            SplashConfiguration
        } else config
        return when (newConfig) {
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

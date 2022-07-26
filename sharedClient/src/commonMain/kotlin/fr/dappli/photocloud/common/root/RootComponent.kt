package fr.dappli.photocloud.common.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import fr.dappli.photocloud.common.db.Database
import fr.dappli.photocloud.common.db.DatabaseDriverFactory
import fr.dappli.photocloud.common.home.HomeComponent
import fr.dappli.photocloud.common.login.LoginComponent
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.photocloud.common.root.Root.Child
import fr.dappli.photocloud.common.splash.SplashComponent

class RootComponent(
    componentContext: ComponentContext,
    databaseDriverFactory: DatabaseDriverFactory
) : Root, ComponentContext by componentContext {

    // it could be injected to components
    private val database = Database(databaseDriverFactory)
    private val photocloudLoader = PhotocloudLoader(database)

    private val router: Router<Config, Child> = router(
        initialConfiguration = Config.Login,
        handleBackButton = true, // Pop the back stack on back button press
        childFactory = ::createConfig
    )

    override val routerState: Value<RouterState<*, Child>> = router.state

    private fun createConfig(config: Config, context: ComponentContext): Child {
        val newConfig = if (config is Config.Login && photocloudLoader.isLoggedIn) {
            Config.Splash
        } else config
        return when (newConfig) {
            is Config.Login -> Child.LoginChild(
                LoginComponent(context, photocloudLoader) {
                    router.replaceCurrent(Config.Splash)
                }
            )
            is Config.Splash -> Child.SplashChild(
                SplashComponent(context, photocloudLoader) { config ->
                    router.replaceCurrent(Config.Home(config.rootDir.id))
                }
            )
            is Config.Home -> Child.HomeChild(
                HomeComponent(context, photocloudLoader, newConfig.rootDirId) {
                    router.replaceCurrent(Config.Login)
                }
            )
        }
    }

    private sealed class Config : Parcelable {
        @Parcelize
        object Login : Config()
        @Parcelize
        object Splash : Config()
        @Parcelize
        data class Home(val rootDirId: String) : Config()
    }
}

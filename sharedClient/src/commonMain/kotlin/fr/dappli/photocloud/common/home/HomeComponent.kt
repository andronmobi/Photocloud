package fr.dappli.photocloud.common.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.home.model.Screen
import fr.dappli.photocloud.common.home.model.ScreenConfiguration
import fr.dappli.photocloud.common.network.PhotocloudLoader

class HomeComponent(
    componentContext: ComponentContext,
    private val photocloudLoader: PhotocloudLoader
) : Home, ComponentContext by componentContext {

    private val router: Router<ScreenConfiguration, Screen> = router(
        initialConfiguration = ScreenConfiguration.PhotoListConfiguration,
        key = "HomeRouter",
        childFactory = ::createScreen
    )

    override val routerState: Value<RouterState<*, Screen>> = router.state

    override fun onTabHomeClick() {
        router.bringToFront(ScreenConfiguration.PhotoListConfiguration)
    }

    override fun onTabSettingsClick() {
        router.bringToFront(ScreenConfiguration.SettingsConfiguration)
    }

    private fun createScreen(config: ScreenConfiguration, context: ComponentContext): Screen {
        return when (config) {
            ScreenConfiguration.PhotoListConfiguration -> Screen.PhotoListScreen
            ScreenConfiguration.SettingsConfiguration -> Screen.SettingsScreen
        }
    }
}

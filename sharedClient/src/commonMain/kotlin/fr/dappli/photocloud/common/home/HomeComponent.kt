package fr.dappli.photocloud.common.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import fr.dappli.photocloud.common.home.Home.Child
import fr.dappli.photocloud.common.network.PhotocloudLoader

class HomeComponent(
    componentContext: ComponentContext,
    private val photocloudLoader: PhotocloudLoader
) : Home, ComponentContext by componentContext {

    private val router: Router<Config, Child> = router(
        initialConfiguration = Config.PhotoList,
        key = "HomeRouter",
        childFactory = ::createChild
    )

    override val routerState: Value<RouterState<*, Child>> = router.state

    override fun onTabHomeClick() {
        router.bringToFront(Config.PhotoList)
    }

    override fun onTabSettingsClick() {
        router.bringToFront(Config.Settings)
    }

    private fun createChild(config: Config, context: ComponentContext): Child {
        return when (config) {
            Config.PhotoList -> Child.PhotoListChild
            Config.Settings -> Child.SettingsChild
        }
    }

    sealed class Config : Parcelable {
        @Parcelize
        object PhotoList : Config()
        @Parcelize
        object Settings : Config()
    }
}

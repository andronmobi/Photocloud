package fr.dappli.photocloud.common.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import fr.dappli.photocloud.common.home.Home.Child
import fr.dappli.photocloud.common.list.PhotoList
import fr.dappli.photocloud.common.list.PhotoListComponent
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.photocloud.common.utils.toPhotoDir

class HomeComponent(
    componentContext: ComponentContext,
    private val photocloudLoader: PhotocloudLoader,
    rootDirId: String
) : Home, ComponentContext by componentContext {

    private val bottomNavRouter: Router<Config, Child> = router(
        initialConfiguration = Config.Home,
        key = "BottomNavRouter",
        childFactory = ::createChild
    )

    private val homeRouter: Router<PhotoListConfig, PhotoList> = router(
        initialConfiguration = PhotoListConfig(rootDirId, isInitial = true),
        key = "HomeRouter",
        handleBackButton = true,
        childFactory = ::createPhotoList
    )

    override val bottomNavRouterState: Value<RouterState<*, Child>> = bottomNavRouter.state

    override val homeRouterState: Value<RouterState<*, PhotoList>> = homeRouter.state

    override fun onTabHomeClick() {
        bottomNavRouter.bringToFront(Config.Home)
    }

    override fun onTabSettingsClick() {
        bottomNavRouter.bringToFront(Config.Settings)
    }

    private fun createChild(config: Config, context: ComponentContext): Child {
        return when (config) {
            Config.Home -> Child.HomeChild
            Config.Settings -> Child.SettingsChild
        }
    }

    private fun createPhotoList(
        photoListConfig: PhotoListConfig,
        context: ComponentContext
    ): PhotoList {
        return PhotoListComponent(
            componentContext = context,
            photocloudLoader = photocloudLoader,
            currentDir = photoListConfig.dirId.toPhotoDir(),
            isInitial = photoListConfig.isInitial,
            onDirSelected = { dirId ->
                homeRouter.push(PhotoListConfig(dirId, isInitial = false))
            },
            onClose = {
                homeRouter.pop()
            }
        )
    }

    sealed class Config : Parcelable {
        @Parcelize
        object Home : Config()

        @Parcelize
        object Settings : Config()
    }

    @Parcelize
    data class PhotoListConfig(val dirId: String, val isInitial: Boolean) : Parcelable
}

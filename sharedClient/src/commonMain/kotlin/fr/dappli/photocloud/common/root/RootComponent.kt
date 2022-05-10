package fr.dappli.photocloud.common.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import fr.dappli.photocloud.common.list.PhotoListComponent
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.photocloud.common.root.Root.Child
import fr.dappli.photocloud.common.vo.Dir
import fr.dappli.sharedclient.Platform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RootComponent(
    componentContext: ComponentContext
) : Root, ComponentContext by componentContext {

    private val photocloudLoader = PhotocloudLoader()

    private val router: Router<ChildConfiguration, Child> = router(
        initialConfiguration = ChildConfiguration.LoadingConfiguration,
        handleBackButton = true, // Pop the back stack on back button press
        childFactory = ::createChild
    )

    override val routerState: Value<RouterState<*, Child>> = router.state

    init {
        CoroutineScope(Platform.uiDispatcher).launch {
            val config = photocloudLoader.getConfig()
            router.bringToFront(
                ChildConfiguration.ListConfiguration(config.rootDir.id, isInitial = true)
            )
        }
    }

    private fun createChild(config: ChildConfiguration, context: ComponentContext): Child {
        return when (config) {
            is ChildConfiguration.LoadingConfiguration -> Child.LoadingChild
            is ChildConfiguration.ListConfiguration -> {
                Child.ListChild(
                    PhotoListComponent(
                        context,
                        photocloudLoader,
                        Dir(config.dirId),
                        config.isInitial,
                        ::onDirSelected,
                        ::onClose
                    )
                )
            }
        }
    }

    private fun onDirSelected(dir: Dir) {
        router.push(ChildConfiguration.ListConfiguration(dir.id, isInitial = false))
    }

    private fun onClose() {
        router.pop()
    }

    sealed class ChildConfiguration : Parcelable {
        @Parcelize
        object LoadingConfiguration : ChildConfiguration()

        @Parcelize
        data class ListConfiguration(val dirId: String, val isInitial: Boolean) :
            ChildConfiguration()
    }

}

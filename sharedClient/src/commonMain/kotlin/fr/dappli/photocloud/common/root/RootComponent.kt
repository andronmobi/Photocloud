package fr.dappli.photocloud.common.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import fr.dappli.photocloud.common.list.PhotoListComponent
import fr.dappli.photocloud.common.root.Root.Child
import fr.dappli.photocloud.common.vo.Dir

@OptIn(ExperimentalDecomposeApi::class)
class RootComponent(
    componentContext: ComponentContext
) : Root, ComponentContext by componentContext {

    private val router: Router<ChildConfiguration, Child> = router(
        initialConfiguration = ChildConfiguration("000", isInitial = true),
        handleBackButton = true, // Pop the back stack on back button press
        childFactory = ::createChild
    )

    override val routerState: Value<RouterState<*, Child>> = router.state

    private fun createChild(config: ChildConfiguration, context: ComponentContext): Child = Child(
        PhotoListComponent(context, config.isInitial, ::onDirSelected, ::onClose).also {
            println("andrei create child: ${config.dirId}")
        }
    )

    private fun onDirSelected(dir: Dir) {
        println("andrei to add dir/conf $dir.id")
        router.push(ChildConfiguration(dir.id, isInitial = false))
    }

    private fun onClose() {
        router.pop()
    }

    @Parcelize
    private data class ChildConfiguration(val dirId: String, val isInitial: Boolean) : Parcelable
}
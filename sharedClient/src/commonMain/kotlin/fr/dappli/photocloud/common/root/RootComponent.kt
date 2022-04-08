package fr.dappli.photocloud.common.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import fr.dappli.photocloud.common.list.PhotoListComponent
import fr.dappli.photocloud.common.root.Root.Child

class RootComponent(
    componentContext: ComponentContext
) : Root, ComponentContext by componentContext {

    private val router: Router<ChildConfiguration, Child> = router(
        initialConfiguration = ChildConfiguration(0),
        handleBackButton = true, // Pop the back stack on back button press
        childFactory = ::createChild
    )

    override val routerState: Value<RouterState<*, Child>> = router.state

    private fun createChild(config: ChildConfiguration, context: ComponentContext): Child = Child(
        PhotoListComponent(context)
    )

    @Parcelize
    private data class ChildConfiguration(val dirId: Int) : Parcelable
}

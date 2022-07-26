package fr.dappli.photocloud.common.home

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.list.PhotoList
import fr.dappli.photocloud.common.settings.Settings

interface Home {
    val bottomNavRouterState: Value<RouterState<*, Child>>
    val homeRouterState: Value<RouterState<*, PhotoList>>

    fun onTabHomeClick()
    fun onTabSettingsClick()

    sealed class Child {
        object HomeChild : Child()
        class SettingsChild(val component: Settings) : Child()
    }
}

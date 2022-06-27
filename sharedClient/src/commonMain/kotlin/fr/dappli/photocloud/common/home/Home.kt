package fr.dappli.photocloud.common.home

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.home.model.Screen

interface Home {
    val routerState: Value<RouterState<*, Screen>>

    fun onTabHomeClick()
    fun onTabSettingsClick()
}

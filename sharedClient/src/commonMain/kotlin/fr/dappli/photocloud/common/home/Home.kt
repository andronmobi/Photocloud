package fr.dappli.photocloud.common.home

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value

interface Home {
    val routerState: Value<RouterState<*, Child>>

    fun onTabHomeClick()
    fun onTabSettingsClick()

    sealed class Child {
        //class PhotoListScreen(val component: PhotoList) : Child()
        object PhotoListChild : Child()
        object SettingsChild : Child()
    }
}

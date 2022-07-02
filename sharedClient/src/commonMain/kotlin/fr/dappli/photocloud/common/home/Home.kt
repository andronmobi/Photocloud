package fr.dappli.photocloud.common.home

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.list.PhotoList

interface Home {
    val routerState: Value<RouterState<*, Child>>

    fun onTabHomeClick()
    fun onTabSettingsClick()

    sealed class Child {
        class PhotoListChild(val component: PhotoList) : Child()
        object SettingsChild : Child()
    }
}

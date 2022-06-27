package fr.dappli.photocloud.common.home.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class ScreenConfiguration : Parcelable {
    @Parcelize
    object PhotoListConfiguration : ScreenConfiguration()
    @Parcelize
    object SettingsConfiguration : ScreenConfiguration()
}

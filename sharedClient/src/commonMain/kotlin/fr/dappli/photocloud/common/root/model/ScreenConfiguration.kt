package fr.dappli.photocloud.common.root.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class ScreenConfiguration : Parcelable {
    @Parcelize
    object LoginConfiguration : ScreenConfiguration()
    @Parcelize
    object SplashConfiguration : ScreenConfiguration()
    @Parcelize
    object HomeConfiguration : ScreenConfiguration()
}

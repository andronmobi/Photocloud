package fr.dappli.photocloud.common.vo

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val accessToken: String,
    val refreshToken: String
)

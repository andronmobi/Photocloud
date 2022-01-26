package fr.dappli.photocloud.vo

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val rootDir: Dir
)

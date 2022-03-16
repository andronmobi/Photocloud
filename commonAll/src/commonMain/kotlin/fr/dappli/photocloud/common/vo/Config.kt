package fr.dappli.photocloud.common.vo

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val rootDir: Dir
)

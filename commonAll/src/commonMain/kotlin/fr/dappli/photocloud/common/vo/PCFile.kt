package fr.dappli.photocloud.common.vo

import kotlinx.serialization.Serializable

@Serializable
sealed class PCFile {
    abstract val id: String
}

@Serializable
data class Dir(override val id: String) : PCFile()

@Serializable
data class Photo(override val id: String) : PCFile()

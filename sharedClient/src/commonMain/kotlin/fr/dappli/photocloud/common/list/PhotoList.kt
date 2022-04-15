package fr.dappli.photocloud.common.list

import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.vo.Dir
import fr.dappli.photocloud.common.vo.PCFile
import fr.dappli.photocloud.common.vo.Photo

interface PhotoList {

    val currentDir: Dir
    val isInitial: Boolean
    val models: Value<Model>

    fun onDirClicked(dir: Dir)
    fun onBackClicked()

    data class Model(
        val dirs: List<Dir>,
        val images: List<ByteArray>
    )
}

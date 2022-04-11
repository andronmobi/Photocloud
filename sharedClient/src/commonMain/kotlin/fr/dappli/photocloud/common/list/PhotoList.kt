package fr.dappli.photocloud.common.list

import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.vo.Dir
import fr.dappli.photocloud.common.vo.PCFile
import fr.dappli.photocloud.common.vo.Photo

interface PhotoList {

    val isInitial: Boolean
    val models: Value<Model>

    fun onDirClicked(dir: Dir)
    fun onBackClicked()

    data class Model(
        val photos: List<Photo>,
        val files: List<PCFile>
    )
}

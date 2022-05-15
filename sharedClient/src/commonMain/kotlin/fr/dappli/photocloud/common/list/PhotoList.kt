package fr.dappli.photocloud.common.list

import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.list.model.PhotoDir
import fr.dappli.photocloud.common.list.model.PhotoImage
import fr.dappli.photocloud.common.vo.Dir
import fr.dappli.photocloud.common.vo.Photo

interface PhotoList {

    val currentDir: PhotoDir
    val isInitial: Boolean
    val models: Value<Model>

    fun onDirClicked(dirId: String)
    fun onBackClicked()

    data class Model(
        val dirs: List<PhotoDir>,
        val photoImages: List<PhotoImage>
    )
}

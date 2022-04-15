package fr.dappli.photocloud.common.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.photocloud.common.vo.Dir
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class PhotoListComponent(
    componentContext: ComponentContext,
    photocloudLoader: PhotocloudLoader,
    override val currentDir: Dir,
    override val isInitial: Boolean,
    private val onDirSelected: (Dir) -> Unit,
    private val onClose: () -> Unit
) : PhotoList, ComponentContext by componentContext {

    private val _models = MutableValue(PhotoList.Model(emptyList()))

    override val models: Value<PhotoList.Model> = _models

    override fun onDirClicked(dir: Dir) {
        onDirSelected(dir)
    }

    override fun onBackClicked() {
        onClose()
    }

    init {
        MainScope().launch {
            _models.value = PhotoList.Model(photocloudLoader.getFiles(currentDir))
        }
    }
}

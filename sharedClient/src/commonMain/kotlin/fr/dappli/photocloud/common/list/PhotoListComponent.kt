package fr.dappli.photocloud.common.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.reduce
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.photocloud.common.vo.Dir
import fr.dappli.photocloud.common.vo.Photo
import fr.dappli.sharedclient.Platform
import kotlinx.coroutines.launch

class PhotoListComponent(
    componentContext: ComponentContext,
    photocloudLoader: PhotocloudLoader,
    override val currentDir: Dir,
    override val isInitial: Boolean,
    private val onDirSelected: (Dir) -> Unit,
    private val onClose: () -> Unit
) : PhotoList, ComponentContext by componentContext {

    private val _models = MutableValue(PhotoList.Model(emptyList(), emptyList()))

    override val models: Value<PhotoList.Model> = _models

    override fun onDirClicked(dir: Dir) {
        onDirSelected(dir)
    }

    override fun onBackClicked() {
        onClose()
    }

    init {
        Platform.mainCoroutineScope.launch {
            val files = photocloudLoader.getFiles(currentDir)
            val photoFiles = files.filterIsInstance<Photo>()
            val dirs = files.filterIsInstance<Dir>()

            _models.value = PhotoList.Model(dirs, emptyList())
            val images = mutableListOf<PhotoList.PhotoImage>()
            photoFiles.forEach { photo ->
                val image = photocloudLoader.getImageData(photo.id)
                images.add(PhotoList.PhotoImage(photo, image))
                _models.reduce { model ->
                    model.copy(photoImages = images.toList())
                }
            }
        }
    }
}

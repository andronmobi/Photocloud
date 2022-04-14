package fr.dappli.photocloud.common.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.photocloud.common.vo.Dir
import fr.dappli.photocloud.common.vo.Photo
import kotlin.native.concurrent.ThreadLocal

class PhotoListComponent(
    componentContext: ComponentContext,
    photocloudLoader: PhotocloudLoader,
    override val isInitial: Boolean,
    private val onDirSelected: (Dir) -> Unit,
    private val onClose: () -> Unit
) : PhotoList, ComponentContext by componentContext {

    private val _models = MutableValue(
        PhotoList.Model(
            listOf(Photo("$id-12345")),  // TODO just for test
            listOf(Photo("$id-67895"), Dir("00$id"))
        ).also {
            id++
        }
    )

    override val models: Value<PhotoList.Model> = _models

    override fun onDirClicked(dir: Dir) {
        onDirSelected(dir)
    }

    override fun onBackClicked() {
        onClose()
    }

    @ThreadLocal
    private companion object {
        var id = 1
    }
}

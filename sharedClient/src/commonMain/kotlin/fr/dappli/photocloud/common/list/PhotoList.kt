package fr.dappli.photocloud.common.list

import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.vo.Dir
import fr.dappli.photocloud.common.vo.Photo

interface PhotoList {

    val currentDir: Dir
    val isInitial: Boolean
    val models: Value<Model>

    fun onDirClicked(dir: Dir)
    fun onBackClicked()

    data class Model(
        val dirs: List<Dir>,
        val photoImages: List<PhotoImage>
    )

    data class PhotoImage(
        val photo: Photo,
        val image: ByteArray
    ) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as PhotoImage

            if (photo != other.photo) return false

            return true
        }

        override fun hashCode(): Int {
            return photo.hashCode()
        }
    }
}

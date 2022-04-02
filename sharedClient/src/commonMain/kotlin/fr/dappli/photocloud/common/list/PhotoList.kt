package fr.dappli.photocloud.common.list

import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.vo.Photo

interface PhotoList {

    val models: Value<Model>

    data class Model(
        val photos: List<Photo>
    )
}

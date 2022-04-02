package fr.dappli.photocloud.common.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.vo.Photo

class PhotoListComponent(
    componentContext: ComponentContext
) : PhotoList, ComponentContext by componentContext {

    private val _models = MutableValue(
        PhotoList.Model(
            listOf(Photo("12345"))  // TODO just for test
        )
    )

    override val models: Value<PhotoList.Model> = _models
}

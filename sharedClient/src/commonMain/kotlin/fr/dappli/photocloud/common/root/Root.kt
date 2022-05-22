package fr.dappli.photocloud.common.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.list.PhotoList
import fr.dappli.photocloud.common.root.model.Screen

interface Root {
    val routerState: Value<RouterState<*, Screen>>
}

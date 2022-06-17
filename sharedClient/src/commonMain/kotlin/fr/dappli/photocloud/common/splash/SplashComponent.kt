package fr.dappli.photocloud.common.splash

import com.arkivanov.decompose.ComponentContext
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.photocloud.common.vo.Config
import fr.dappli.sharedclient.Platform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashComponent(
    componentContext: ComponentContext,
    private val photocloudLoader: PhotocloudLoader,
    private val onSuccess: (Config) -> Unit,
) : Splash, ComponentContext by componentContext {

    init {
        CoroutineScope(Platform.uiDispatcher).launch {
            onSuccess(photocloudLoader.getConfig())
        }
    }
}

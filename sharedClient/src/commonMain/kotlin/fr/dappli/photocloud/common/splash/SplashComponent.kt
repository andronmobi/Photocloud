package fr.dappli.photocloud.common.splash

import com.arkivanov.decompose.ComponentContext
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.photocloud.common.vo.Config
import fr.dappli.sharedclient.Platform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SplashComponent(
    componentContext: ComponentContext,
    private val photocloudLoader: PhotocloudLoader,
    private val onSuccess: (Config) -> Unit,
    private val onError: (isLogout: Boolean) -> Unit
) : Splash, ComponentContext by componentContext {

    init {
        CoroutineScope(Platform.uiDispatcher).launch {
            try {
                onSuccess(photocloudLoader.getConfig())
            } catch (ex: Throwable) {
                println("getConfig error: $ex")
                // TODO we should not always logout only if 401 (Unauthorized)
                photocloudLoader.logout()
                onError(true)
            }
        }
    }
}

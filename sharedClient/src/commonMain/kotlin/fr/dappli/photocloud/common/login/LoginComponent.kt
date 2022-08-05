package fr.dappli.photocloud.common.login

import com.arkivanov.decompose.ComponentContext
import fr.dappli.photocloud.common.ClientConfig.DEFAULT_HOST
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.sharedclient.Platform
import kotlinx.coroutines.*

class LoginComponent(
    componentContext: ComponentContext,
    private val photocloudLoader: PhotocloudLoader,
    private val onLoginSuccess: () -> Unit,
) : Login, ComponentContext by componentContext {

    override fun login(name: String, password: String, host: String) {
        println("login $name $password $host")
        CoroutineScope(Platform.uiDispatcher).launch {
            // TODO loading
            if (photocloudLoader.login(name, password, host))
                onLoginSuccess()
            // TODO else error
        }
    }

    override val defaultHost: String = DEFAULT_HOST
}

package fr.dappli.photocloud.common.login

import com.arkivanov.decompose.ComponentContext
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.sharedclient.Platform
import kotlinx.coroutines.*

class LoginComponent(
    componentContext: ComponentContext,
    private val photocloudLoader: PhotocloudLoader,
    private val onLoginSuccess: () -> Unit,
) : Login, ComponentContext by componentContext {

    override fun login(name: String, password: String) {
        println("login $name $password")
        CoroutineScope(Platform.uiDispatcher).launch {
            // TODO loading
            if (photocloudLoader.login(name, password))
                onLoginSuccess()
            // TODO else error
        }
    }
}

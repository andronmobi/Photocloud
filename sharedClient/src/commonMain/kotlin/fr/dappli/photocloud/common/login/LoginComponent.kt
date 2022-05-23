package fr.dappli.photocloud.common.login

import com.arkivanov.decompose.ComponentContext
import fr.dappli.sharedclient.Platform
import kotlinx.coroutines.*

class LoginComponent(
    componentContext: ComponentContext,
    private val onLoginSuccess: () -> Unit
) : Login, ComponentContext by componentContext {

    override fun login(name: String, password: String) {
        println("login $name $password")
        // TODO do login
        CoroutineScope(Platform.uiDispatcher).launch {
            onLoginSuccess()
        }
    }

}

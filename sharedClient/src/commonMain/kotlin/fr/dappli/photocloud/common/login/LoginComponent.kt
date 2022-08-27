package fr.dappli.photocloud.common.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import fr.dappli.photocloud.common.ClientConfig.DEFAULT_HOST
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.sharedclient.Platform
import io.ktor.client.network.sockets.*
import kotlinx.coroutines.*

class LoginComponent(
    componentContext: ComponentContext,
    private val photocloudLoader: PhotocloudLoader,
    private val onLoginSuccess: () -> Unit
) : Login, ComponentContext by componentContext {

    private val _state = MutableValue<Login.State>(Login.State.None)

    override val state: Value<Login.State>
        get() = _state

    override fun login(name: String, password: String, host: String) {
        when {
            name.isBlank() -> {
                _state.value = Login.State.Error("username can not be empty or blank")
                return
            }
            password.isBlank() -> {
                _state.value = Login.State.Error("password can not be empty or blank")
                return
            }
            host.isBlank() -> {
                _state.value = Login.State.Error("host can not be empty or blank")
                return
            }
        }

        CoroutineScope(Platform.uiDispatcher).launch {
            try {
                _state.value = Login.State.Loading
                if (photocloudLoader.login(name, password, host))
                    onLoginSuccess()
                else {
                    _state.value = Login.State.Error("Authentication failed")
                }
            } catch (e: Throwable) {
                println("login error: $e")
                when (e) {
                    is ConnectTimeoutException -> {
                        _state.value = Login.State.Error("Connect timeout has expired")
                    }
                    else -> {
                        _state.value = Login.State.Error("Exception: ${e.message}")
                    }
                }
            }
        }
    }

    override fun onSnackbarClose() {
        _state.value = Login.State.None
    }

    override val defaultHost: String = DEFAULT_HOST
}

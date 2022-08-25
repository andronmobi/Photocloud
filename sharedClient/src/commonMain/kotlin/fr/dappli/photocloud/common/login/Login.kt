package fr.dappli.photocloud.common.login

import com.arkivanov.decompose.value.Value

interface Login {

    val state: Value<State>
    val defaultHost: String

    fun onSnackbarClose()
    fun login(name: String, password: String, host: String)

    sealed interface State {
        object None : State
        data class Error(val message: String) : State
    }
}

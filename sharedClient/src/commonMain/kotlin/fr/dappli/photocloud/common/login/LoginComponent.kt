package fr.dappli.photocloud.common.login

import com.arkivanov.decompose.ComponentContext

class LoginComponent(
    componentContext: ComponentContext
) : Login, ComponentContext by componentContext {

    override fun login(name: String, password: String) {
        println("login $name $password")
    }

}

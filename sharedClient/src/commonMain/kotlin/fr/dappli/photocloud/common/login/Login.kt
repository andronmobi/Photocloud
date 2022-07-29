package fr.dappli.photocloud.common.login

interface Login {
    fun login(name: String, password: String, host: String)
    val defaultHost: String
}

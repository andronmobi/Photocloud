package fr.dappli.sharedclient

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform.platform}!"
    }
}
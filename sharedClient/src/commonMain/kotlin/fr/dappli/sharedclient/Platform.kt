package fr.dappli.sharedclient

import io.ktor.client.engine.*

expect object Platform {
    val platform: String
    val debugHost: String
    val engineFactory: HttpClientEngineFactory<HttpClientEngineConfig>
}
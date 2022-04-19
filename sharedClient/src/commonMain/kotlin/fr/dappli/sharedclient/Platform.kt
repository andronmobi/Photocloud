package fr.dappli.sharedclient

import io.ktor.client.engine.*
import kotlinx.coroutines.CoroutineScope

expect object Platform {
    val platform: String
    val debugHost: String
    val engineFactory: HttpClientEngineFactory<HttpClientEngineConfig>
    val mainCoroutineScope: CoroutineScope
}

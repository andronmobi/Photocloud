package fr.dappli.sharedclient

import io.ktor.client.engine.*
import kotlin.coroutines.CoroutineContext

expect object Platform {
    val platform: String
    val engineFactory: HttpClientEngineFactory<HttpClientEngineConfig>
    val uiDispatcher: CoroutineContext
    val defaultDispatcher: CoroutineContext
}

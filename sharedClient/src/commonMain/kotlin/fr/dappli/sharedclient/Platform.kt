package fr.dappli.sharedclient

import io.ktor.client.engine.*

expect class Platform() {
    val platform: String
    val debugHost: String
    val engineFactory: HttpClientEngineFactory<HttpClientEngineConfig>
    //val photocloudLoader: PhotocloudLoader
}
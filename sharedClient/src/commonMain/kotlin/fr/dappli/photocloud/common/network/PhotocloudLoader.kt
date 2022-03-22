package fr.dappli.photocloud.common.network

import fr.dappli.photocloud.common.vo.Config
import io.ktor.client.request.*

class PhotocloudLoader {

    suspend fun getConfig(): Config {
        return Network.authClient.get {
            url {
                encodedPath = "config"
            }
        }
    }
}
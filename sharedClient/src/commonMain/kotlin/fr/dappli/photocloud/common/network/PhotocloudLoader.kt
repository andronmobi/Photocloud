package fr.dappli.photocloud.common.network

import fr.dappli.photocloud.common.vo.Config
import fr.dappli.photocloud.common.vo.Dir
import fr.dappli.photocloud.common.vo.PCFile
import io.ktor.client.request.*

class PhotocloudLoader {

    suspend fun getConfig(): Config {
        return Network.authClient.get {
            url {
                encodedPath = "config"
            }
        }
    }

    suspend fun getFiles(dir: Dir): List<PCFile> {
        return Network.authClient.get {
            url {
                encodedPath = "file/${dir.id}"
            }
        }
    }
}
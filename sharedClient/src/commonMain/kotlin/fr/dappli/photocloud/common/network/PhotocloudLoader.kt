package fr.dappli.photocloud.common.network

import fr.dappli.photocloud.common.vo.Config
import fr.dappli.photocloud.common.vo.Dir
import fr.dappli.photocloud.common.vo.PCFile
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class PhotocloudLoader {

    suspend fun login(name: String, password: String): Boolean {
        return Network.login(name, password)
    }

    suspend fun getConfig(): Config {
        return Network.authClient.get {
            url {
                encodedPath = "config"
            }
        }.body()
    }

    suspend fun getFiles(dir: Dir): List<PCFile> {
        return Network.authClient.get {
            url {
                encodedPath = "file/${dir.id}"
            }
        }.body()
    }

    suspend fun getImageData(photoId: String): ByteArray {
        return Network.authClient.get {
            url {
                encodedPath = "file/${photoId}/download"
            }
        }.body()
    }

}

package fr.dappli.photocloud.common.network

import fr.dappli.photocloud.common.db.Database
import fr.dappli.photocloud.common.vo.Config
import fr.dappli.photocloud.common.vo.Dir
import fr.dappli.photocloud.common.vo.PCFile
import fr.dappli.sharedclient.Platform
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class PhotocloudLoader(
    private val database: Database
) {

    private val network = Network(database)

    val isLoggedIn: Boolean
        get() = network.isLoggedIn

    suspend fun login(name: String, password: String): Boolean {
        return network.login(name, password, Platform.debugHost) // TODO
    }

    suspend fun logout() {
        network.logout()
    }

    suspend fun getConfig(): Config {
        return network.authClient.get {
            url {
                host = network.host
                encodedPath = "config"
            }
        }.body()
    }

    suspend fun getFiles(dir: Dir): List<PCFile> {
        return network.authClient.get {
            url {
                host = network.host
                encodedPath = "file/${dir.id}"
            }
        }.body()
    }

    suspend fun getImageData(photoId: String): ByteArray {
        return network.authClient.get {
            url {
                host = network.host
                encodedPath = "file/${photoId}/download"
            }
        }.body()
    }

}

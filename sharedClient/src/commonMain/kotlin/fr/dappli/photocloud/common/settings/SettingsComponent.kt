package fr.dappli.photocloud.common.settings

import com.arkivanov.decompose.ComponentContext
import fr.dappli.photocloud.common.network.PhotocloudLoader
import fr.dappli.sharedclient.Platform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SettingsComponent(
    componentContext: ComponentContext,
    private val photocloudLoader: PhotocloudLoader,
    private val onLogoutSuccess: () -> Unit
) : Settings {

    override fun logout() {
        CoroutineScope(Platform.uiDispatcher).launch {
            photocloudLoader.logout()
            onLogoutSuccess()
        }
    }

}

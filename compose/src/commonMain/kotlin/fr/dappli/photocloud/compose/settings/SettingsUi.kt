package fr.dappli.photocloud.compose.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.dappli.photocloud.common.settings.Settings

@Composable
fun SettingsUi(settings: Settings) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .padding(horizontal = 60.dp),
            onClick = {
                settings.logout()
            }
        ) {
            Text("Logout", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }
    }
}

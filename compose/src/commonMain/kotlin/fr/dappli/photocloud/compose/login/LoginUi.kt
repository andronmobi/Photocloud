package fr.dappli.photocloud.compose.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import fr.dappli.photocloud.common.login.Login

@Composable
fun LoginUi(login: Login) {
    Column {
        Text("Login Screen")
        Button(onClick = {
            login.login("user", "password")
        }) {
            Text("Login")
        }
    }
}

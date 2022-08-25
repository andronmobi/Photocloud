package fr.dappli.photocloud.compose.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import fr.dappli.photocloud.common.login.Login

@Composable
fun LoginUi(login: Login) {
    val state by login.state.subscribeAsState()

    val focusManager = LocalFocusManager.current
    val username = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val host = rememberSaveable { mutableStateOf(login.defaultHost) }

    Box(Modifier.fillMaxSize()) {

        if (state is Login.State.Error) {
            Snackbar(
                modifier = Modifier.padding(10.dp),
                action = {
                   Button(onClick = { login.onSnackbarClose() }) {
                       Text("OK")
                   }
                }
            ) {
                Text((state as Login.State.Error).message)
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Please login",
                style = MaterialTheme.typography.h6
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .padding(horizontal = 60.dp)
                ,
                value = username.value,
                placeholder = { Text(text = "foo") },
                label = { Text(text = "User name") },
                singleLine = true,
                onValueChange = { username.value = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 60.dp),
                value = password.value,
                placeholder = { Text(text = "bar") },
                label = { Text(text = "Password") },
                onValueChange = { password.value = it },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 60.dp),
                value = host.value,
                placeholder = { Text(text = "192.168.1.2") },
                label = { Text(text = "Host name or IP address") },
                onValueChange = { host.value = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            Button(
                modifier = Modifier.padding(top = 32.dp).padding(horizontal = 60.dp),
                onClick = {
                    login.login(username.value, password.value, host.value)
                    focusManager.clearFocus()
                }) {
                Text("Login", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
        }
    }
}

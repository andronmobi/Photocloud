package fr.dappli.photocloud.android

import fr.dappli.photocloud.common.list.PhotoListComponent
import fr.dappli.photocloud.common.App
import com.arkivanov.decompose.defaultComponentContext
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val photoListComponent = PhotoListComponent(defaultComponentContext())

        setContent {
            MaterialTheme {
                App(photoListComponent)
            }
        }
    }
}

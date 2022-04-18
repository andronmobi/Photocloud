package fr.dappli.photocloud.compose.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import fr.dappli.photocloud.common.list.PhotoList
import fr.dappli.photocloud.compose.loadBitmap
import java.io.ByteArrayInputStream

// TODO improve me
private var photoCache = HashMap<Int, ImageBitmap>()

@Composable
fun PhotoListUI(photoList: PhotoList) {
    val model by photoList.models.subscribeAsState()

    LazyColumn {
        items(model.images) { image ->
            val id = image.hashCode()
            val bitmap = photoCache[id] ?: run {
                val stream = ByteArrayInputStream(image)
                loadBitmap(stream).also {
                    photoCache[id] = it
                }
            }
            Image(
                bitmap = bitmap,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.size(16.dp))
        }
    }
}

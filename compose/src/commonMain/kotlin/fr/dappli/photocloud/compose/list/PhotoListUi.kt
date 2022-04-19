package fr.dappli.photocloud.compose.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import fr.dappli.photocloud.common.list.PhotoList
import fr.dappli.photocloud.compose.loadBitmap
import java.io.ByteArrayInputStream
import kotlin.collections.HashMap

// TODO improve me
private var photoCache = HashMap<String, ImageBitmap>()

@Composable
fun PhotoListUI(photoList: PhotoList) {
    val model by photoList.models.subscribeAsState()
    PhotoGrid(model.photoImages)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoGrid(photoImages: List<PhotoList.PhotoImage>) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(116.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(photoImages) { photoImage ->
            Card(
                backgroundColor = Color.LightGray,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                val bitmap = photoCache[photoImage.photo.id] ?: run {
                    val stream = ByteArrayInputStream(photoImage.image)
                    loadBitmap(stream).also {
                        photoCache[photoImage.photo.id] = it
                    }
                }
                Image(
                    bitmap = bitmap,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

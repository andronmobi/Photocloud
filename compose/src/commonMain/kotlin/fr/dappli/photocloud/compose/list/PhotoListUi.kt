package fr.dappli.photocloud.compose.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import fr.dappli.photocloud.common.list.PhotoList
import fr.dappli.photocloud.common.list.model.PhotoDir
import fr.dappli.photocloud.common.list.model.PhotoImage
import fr.dappli.photocloud.compose.iconDirPainter
import fr.dappli.photocloud.compose.loadBitmap
import java.io.ByteArrayInputStream
import kotlin.collections.HashMap

// TODO improve me
private var photoCache = HashMap<String, ImageBitmap>()

@Composable
fun PhotoListUI(photoList: PhotoList) {
    val model by photoList.models.subscribeAsState()
    Column {
        Folders(model.dirs) { dirId ->
            photoList.onDirClicked(dirId)
        }
        Spacer(modifier = Modifier.size(4.dp))
        PhotoGrid(model.photoImages)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Folders(dirs: List<PhotoDir>, onDirClicked: (String) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(8.dp)
    ) {
        items(dirs) { photoDir ->
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = iconDirPainter(),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                    modifier = Modifier.size(120.dp).clickable {
                        onDirClicked(photoDir.id)
                    }
                )
                Text(
                    fontSize = 12.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    text = photoDir.name,
                    modifier = Modifier.width(116.dp).padding(start = 8.dp, end = 8.dp, top = 16.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoGrid(photoImages: List<PhotoImage>) {
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
                val bitmap = photoCache[photoImage.id] ?: run {
                    val stream = ByteArrayInputStream(photoImage.image)
                    loadBitmap(stream).also {
                        photoCache[photoImage.id] = it
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

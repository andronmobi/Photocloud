package fr.dappli.photocloud.compose.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.dappli.photocloud.compose.iconCloudPainter

@Composable
fun SplashUi() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(160.dp),
                painter = iconCloudPainter(),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
            Text(
                fontSize = 14.sp,
                color = Color.White,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                text = "Photocloud",
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 16.dp)
            )
        }
    }
}

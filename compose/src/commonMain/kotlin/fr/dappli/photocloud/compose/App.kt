package fr.dappli.photocloud.compose

import androidx.compose.runtime.Composable
import fr.dappli.photocloud.common.root.Root
import fr.dappli.photocloud.compose.listing.FileListScreen

@Composable
fun App(root: Root) {
    FileListScreen(root)
}

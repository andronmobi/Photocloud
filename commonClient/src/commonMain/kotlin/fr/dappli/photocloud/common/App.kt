package fr.dappli.photocloud.common

import androidx.compose.runtime.Composable
import fr.dappli.photocloud.common.list.PhotoList
import fr.dappli.photocloud.common.root.Root
import fr.dappli.photocloud.common.root.RootComponent
import fr.dappli.photocloud.common.ui.listing.FileListScreen

@Composable
fun App(root: Root) {
    FileListScreen(root)
}

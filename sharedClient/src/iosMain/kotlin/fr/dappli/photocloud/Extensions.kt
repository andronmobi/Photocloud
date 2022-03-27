package fr.dappli.photocloud

import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toCValues
import platform.Foundation.*
import platform.UIKit.UIImage

fun ByteArray.toUiImage(): UIImage? {
    return memScoped {
        toCValues()
            .ptr
            .let { NSData.dataWithBytes(it, size.toULong()) }
            .let { UIImage.imageWithData(it) }
    }
}
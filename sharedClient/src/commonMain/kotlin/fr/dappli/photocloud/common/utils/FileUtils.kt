package fr.dappli.photocloud.common.utils

import fr.dappli.photocloud.common.list.model.PhotoDir
import fr.dappli.photocloud.common.vo.Dir
import io.ktor.util.decodeBase64String

fun String.decodeFileId(): String {
    return decodeBase64String()
        .substringAfter("photocloud:///")
        .substringBeforeLast("/")
        .split("/").last()
}

fun PhotoDir.toDir() = Dir(id)

fun Dir.toPhotoDir() = PhotoDir(id, id.decodeFileId())

fun String.toPhotoDir() = PhotoDir(this, this.decodeFileId())

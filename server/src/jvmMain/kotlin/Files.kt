import fr.dappli.photocloud.vo.Dir
import fr.dappli.photocloud.vo.PCFile
import fr.dappli.photocloud.vo.Photo
import io.ktor.features.*
import io.ktor.http.*
import java.io.File
import java.util.*

fun getFiles(rootPath: String, fileLocation: String) : List<PCFile> {
    val file = File("$rootPath$fileLocation")
    if (!file.isDirectory) {
        BadRequestException("not supported")
    }
    val files = file.listFiles()
    return files.mapNotNull {
        val fileName = "${fileLocation}/${it.name}"
        when {
            it.isDirectory -> {
                Dir(encodeFileName(fileName))
            }
            it.isImage() -> {
                Photo(encodeFileName(fileName))
            }
            else -> null
        }
    }
}

fun encodeFileName(fileName: String): String =
    Base64.getUrlEncoder().withoutPadding().encodeToString(fileName.toByteArray())

private fun File.isImage(): Boolean {
    return ContentType.fromFilePath(path).find {
        it.match(ContentType("image", "*"))
    } != null
}

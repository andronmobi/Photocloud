import fr.dappli.photocloud.vo.Dir
import fr.dappli.photocloud.vo.PCFile
import fr.dappli.photocloud.vo.Photo
import io.ktor.features.*
import io.ktor.http.*
import java.io.File
import java.util.*

const val SCHEME_NAME = "photocloud"
const val SCHEME ="$SCHEME_NAME://"

fun getFiles(rootPath: String, fileLocation: String) : List<PCFile> {
    val file = File("$rootPath$fileLocation")
    if (!file.isDirectory) {
        BadRequestException("not supported")
    }
    val files = file.listFiles()
    return files.mapNotNull {
        val fileLocation = "$SCHEME$fileLocation${it.name}"
        when {
            it.isDirectory -> {
                Dir(encodeFileLocation("$fileLocation/"))
            }
            it.isImage() -> {
                Photo(encodeFileLocation(fileLocation))
            }
            else -> null
        }
    }
}

fun encodeFileLocation(fileLocation: String): String =
    Base64.getUrlEncoder().withoutPadding().encodeToString(fileLocation.toByteArray())

private fun File.isImage(): Boolean {
    return ContentType.fromFilePath(path).find {
        it.match(ContentType("image", "*"))
    } != null
}

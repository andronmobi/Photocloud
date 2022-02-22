import fr.dappli.photocloud.vo.Dir
import fr.dappli.photocloud.vo.PCFile
import fr.dappli.photocloud.vo.Photo
import io.ktor.features.*
import io.ktor.http.*
import net.coobird.thumbnailator.Thumbnails
import java.io.File
import java.util.*

const val SCHEME_NAME = "photocloud"
const val SCHEME = "$SCHEME_NAME://"

fun getFiles(rootPath: String, fileLocation: String): List<PCFile> {
    val file = File("$rootPath$fileLocation")
    if (!file.isDirectory) {
        BadRequestException("not supported")
    }
    val files = file.listFiles()
    return files.mapNotNull {
        val fileLocation = "$SCHEME$fileLocation${it.name}"
        when {
            it.isDirectory -> {
                if (it.name.startsWith(".")) null // ignore .thumbnails
                else Dir(encodeFileLocation("$fileLocation/"))
            }
            it.isImage() -> {
                Photo(encodeFileLocation(fileLocation))
            }
            else -> null
        }
    }
}

fun Parameters.getFileLocation(): String {
    val fileId = get("fileId") ?: throw BadRequestException("fileId is null")
    val fileUrl = String(Base64.getUrlDecoder().decode(fileId))
    return schemePathRegex.matchEntire(fileUrl)?.groups?.get(1)?.value ?: throw BadRequestException("unrecognized")
}

fun createThumbnail(rootPath: String, fileLocation: String): File {
    val file = File("$rootPath$fileLocation")
    val pathAndName = fileLocation.toPathAndName()
    val thumbnailPath = "$rootPath${pathAndName.first}.thumbnails"
    val thumbnailDir = File(thumbnailPath)
    if (!thumbnailDir.exists()) thumbnailDir.mkdir()
    val thumbnailFullName = "$thumbnailPath/${pathAndName.second}"
    val thumbnailFile = File(thumbnailFullName)
    if (!thumbnailFile.exists()) {
        println("Create thumbnails: $thumbnailFullName")
        Thumbnails.of(file)
            .size(200, 200)
            .toFile(thumbnailFullName)
    }
    return thumbnailFile
}

fun String.toPathAndName(): Pair<String, String> {
    return localPathRegex.matchEntire(this)?.groups?.let { groups ->
        val path = groups[1]?.value ?: throw BadRequestException("unrecognized file path")
        val name = groups[2]?.value ?: throw BadRequestException("unrecognized file name")
        path to name
    } ?: throw BadRequestException("unrecognized file path pattern")
}

fun encodeFileLocation(fileLocation: String): String =
    Base64.getUrlEncoder().withoutPadding().encodeToString(fileLocation.toByteArray())

private fun File.isImage(): Boolean {
    return ContentType.fromFilePath(path).find {
        it.match(ContentType("image", "*"))
    } != null
}

val schemePathRegex = "$SCHEME_NAME:\\/\\/(.*)".toRegex()
val localPathRegex = "^(\\/?.*\\/)*(.+)\$".toRegex()


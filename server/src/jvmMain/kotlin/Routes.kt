import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import fr.dappli.photocloud.vo.Config
import fr.dappli.photocloud.vo.Dir
import fr.dappli.photocloud.vo.User
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File
import java.util.*

fun Route.handleHomeRequests() {
    get("/") {
        call.respondText("Hello, Server!")
    }
}

fun Route.handleLoginRequests(
    secret: String,
    issuer: String,
    audience: String,
    confUser: User,
    tokenDuration: Long
) {
    post("/login") {
        val user = call.receive<User>()
        // Check username and password
        if (user == confUser) {
            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", user.username)
                .withExpiresAt(Date(System.currentTimeMillis() + tokenDuration))
                .sign(Algorithm.HMAC256(secret))
            call.respond(hashMapOf("token" to token))
        } else {
            call.response.status(HttpStatusCode.Forbidden)
        }
    }
}

fun Route.handleConfigRequests() {
    val root = "$SCHEME/"
    val fileId = encodeFileLocation(root)
    val config = Config(Dir(fileId))

    get("/config") {
        call.respond(config)
    }
}

fun Route.handleFileRequests(rootPath: String) {
    get("/file/{fileId}") {
        val fileLocation = call.parameters.getFileLocation()
        val files = getFiles(rootPath, fileLocation)
        call.respond(files)
    }
}

fun Route.handleFileDownloadRequests(rootPath: String) {
    get("/file/{fileId}/download") {
        val fileLocation = call.parameters.getFileLocation()
        val file = File("$rootPath$fileLocation")
        if (file.exists()) {
            call.respondFile(file)
        }
        else call.respond(HttpStatusCode.NotFound)
    }
}

private fun Parameters.getFileLocation(): String {
    val fileId = get("fileId") ?: throw BadRequestException("fileId is null")
    val fileUrl = String(Base64.getUrlDecoder().decode(fileId))
    return pathRegex.matchEntire(fileUrl)?.groups?.get(1)?.value ?: throw BadRequestException("unrecognized")
}

private val pathRegex = "$SCHEME_NAME:\\/\\/(.*)".toRegex()
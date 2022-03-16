import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import fr.dappli.photocloud.common.vo.Config
import fr.dappli.photocloud.common.vo.Dir
import fr.dappli.photocloud.common.vo.User
import io.ktor.application.*
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

// TODO create a dedicated path for thumbnails
fun Route.handleFileDownloadRequests(rootPath: String) {
    get("/file/{fileId}/download") {
        val fileLocation = call.parameters.getFileLocation()
        val file = File("$rootPath$fileLocation")
        if (file.exists()) {
            val thumbnail = createThumbnail(rootPath, fileLocation)
            call.respondFile(thumbnail)
        }
        else call.respond(HttpStatusCode.NotFound)
    }
}
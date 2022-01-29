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
    val fileId = Base64.getUrlEncoder().withoutPadding().encodeToString(root.toByteArray())
    val config = Config(Dir(fileId))

    get("/config") {
        call.respond(config)
    }
}

fun Route.handleFileRequests(rootPath: String) {
    get("/file/{fileId}") {
        val fileId = call.parameters["fileId"] ?: throw BadRequestException("fileId is null")
        val fileUrl = String(Base64.getUrlDecoder().decode(fileId))
        val fileLocation = pathRegex.matchEntire(fileUrl)?.groups?.get(1)?.value ?: throw BadRequestException("unrecognized")
        val files = File("$rootPath$fileLocation").listFiles()
        val fileNames = files?.map { it.name } ?: emptyList()
        call.respondText("$fileLocation>: $fileNames")
    }
}

private const val SCHEME ="photocloud://"
private val pathRegex = "photocloud:\\/\\/(.*)".toRegex()
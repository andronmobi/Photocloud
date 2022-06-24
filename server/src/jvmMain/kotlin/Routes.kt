import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import fr.dappli.photocloud.common.vo.Config
import fr.dappli.photocloud.common.vo.Dir
import fr.dappli.photocloud.common.vo.Token
import fr.dappli.photocloud.common.vo.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.util.*

fun Route.handleHomeRequests() {
    get("/") {
        call.respondText("Hello, Server!")
    }
}

fun Route.handleAudienceRequests() {
    get("/hello") {
        val principal = call.principal<JWTPrincipal>()
        val username = principal!!.payload.getClaim("username").asString()
        val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
        call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
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
            val token = createToken(user.username, secret, issuer, audience, tokenDuration)
            call.respond(token)
        } else {
            call.response.status(HttpStatusCode.Forbidden)
        }
    }
}

fun Route.handleRefreshTokenRequests(
    secret: String,
    issuer: String,
    audience: String,
    tokenDuration: Long
) {
    post("/refresh") {
        val clientToken = call.receive<Token>()
        println("received client token: $clientToken")
        val decodedToken = JWT.decode(clientToken.accessToken)
        val username = decodedToken.getClaim("username").asString()
        // TODO find and compare db refresh token with a client one, check expiration
        val token = createToken(username, secret, issuer, audience, tokenDuration)
        call.respond(token)
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

private fun createToken(
    username: String,
    secret: String,
    issuer: String,
    audience: String,
    tokenDuration: Long
): Token {
    val accessToken = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("username", username)
        .withExpiresAt(Date(System.currentTimeMillis() + tokenDuration))
        .sign(Algorithm.HMAC256(secret))
    val refreshToken = UUID.randomUUID().toString()
    // TODO save to db a refresh token (per user)
    return Token(accessToken, refreshToken)
}

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import fr.dappli.photocloud.vo.Config
import fr.dappli.photocloud.vo.Dir
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import java.util.*
import fr.dappli.photocloud.vo.User
import java.io.File

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    routing {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Delete)
            anyHost()
        }
        install(Compression) {
            gzip()
        }

        val secret = environment.config.property("jwt.secret").getString()
        val issuer = environment.config.property("jwt.issuer").getString()
        val audience = environment.config.property("jwt.audience").getString()
        val myRealm = environment.config.property("jwt.realm").getString()

        val confUserName = environment.config.property("login.username").getString()
        val confPassword = environment.config.property("login.password").getString()
        val confUser = User(confUserName, confPassword)

        val filePath = environment.config.property("server.filePath").getString()
        val fileId = UUID.nameUUIDFromBytes(filePath.toByteArray()).toString()
        val config = Config(Dir(fileId))

        install(Authentication) {
            jwt("auth-jwt") {
                realm = myRealm
                verifier(
                    JWT.require(Algorithm.HMAC256(secret))
                        .withAudience(audience)
                        .withIssuer(issuer)
                        .build()
                )
                validate { credential ->
                    if (credential.payload.getClaim("username").asString() != "") {
                        JWTPrincipal(credential.payload)
                    } else {
                        null
                    }
                }
            }
        }

        post("/login") {
            val user = call.receive<User>()
            // Check username and password
            if (user == confUser) {
                val token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withClaim("username", user.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 30 * 60000))
                    .sign(Algorithm.HMAC256(secret))
                call.respond(hashMapOf("token" to token))
            } else {
                call.response.status(HttpStatusCode.Forbidden)
            }
        }

        authenticate("auth-jwt") {
            get("/config") {
                call.respond(config)
            }

            get("files") {
                val files = File(filePath).listFiles()
                val fileNames = files?.map { it.name } ?: emptyList()
                call.respondText("List of files in $filePath: $fileNames")
            }
        }

        get("/") {
            call.respondText("Hello, Server!")
        }
    }

}

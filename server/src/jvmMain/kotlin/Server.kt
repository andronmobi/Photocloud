import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import fr.dappli.photocloud.common.vo.User
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()
    val tokenDuration = environment.config.property("jwt.tokenDuration").getString().toLong()

    val confUserName = environment.config.property("login.username").getString()
    val confPassword = environment.config.property("login.password").getString()
    val confUser = User(confUserName, confPassword)

    val filePath = environment.config.property("server.filePath").getString()

    setupServer()
    setupAuthentication(secret, issuer, audience, myRealm)

    routing {
        handleLoginRequests(secret, issuer, audience, confUser, tokenDuration)
        handleHomeRequests()

        authenticate("auth-jwt") {
            handleConfigRequests()
            handleFileRequests(filePath)
            handleFileDownloadRequests(filePath)
        }
    }
}

private fun Application.setupServer() {
    install(ContentNegotiation) {
        json(
            // TODO for ktor 2.0.0 probably we can use a default one
            Json {
                encodeDefaults = true
                isLenient = true
                allowSpecialFloatingPointValues = true
                allowStructuredMapKeys = true
                prettyPrint = false
                useArrayPolymorphism = false // the same format for sealed class for client and server
            })
    }
    install(CORS) {
        allowMethod(HttpMethod.Delete)
        anyHost()
    }
    install(Compression) {
        gzip()
    }
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
}

private fun Application.setupAuthentication(
    secret: String,
    issuer: String,
    audience: String,
    myRealm: String
) {
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
}

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.serialization.*
import fr.dappli.photocloud.vo.User

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    routing {
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
        handleLoginRequests(secret, issuer, audience, confUser, tokenDuration)
        handleHomeRequests()

        authenticate("auth-jwt") {
            handleConfigRequests()
            handleFileRequests(filePath)
        }
    }
}

private fun Application.setupServer() {
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

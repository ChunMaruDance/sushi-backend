package chunmaru.ua.features.login


import chunmaru.ua.databse.tokens.TokenDTO
import chunmaru.ua.databse.tokens.TokensModel
import chunmaru.ua.databse.users.UserModel
import chunmaru.ua.utils.PasswordUtils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun loginUser() {
        val receive = call.receive(LoginReceiveRemote::class)
        val userDTO = UserModel.fetchUser(receive.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest)
        } else {

            if (PasswordUtils.verifyPassword(receive.password, userDTO.passwordHash, userDTO.passwordSalt)) {
                val newToken = UUID.randomUUID().toString()
                TokensModel.insert(
                    TokenDTO(
                        id = 0,
                        token = newToken,
                        login = receive.login
                    )
                )
                call.respond(LoginResponseRemote(token = newToken))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }

    }

}
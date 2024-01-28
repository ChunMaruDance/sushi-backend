package chunmaru.ua.features.login


import chunmaru.ua.database.admins.AdminsModel
import chunmaru.ua.database.tokens.TokenDTO
import chunmaru.ua.database.tokens.TokensModel
import chunmaru.ua.database.users.UserModel
import chunmaru.ua.utils.PasswordUtils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun loginUser() {
        val receive = call.receive(LoginReceiveRemote::class)
        val userDTO = AdminsModel.getAdminByLogin(receive.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest)
        } else {

            if (PasswordUtils.verifyPassword(receive.password, userDTO.passwordHash, userDTO.passwordSalt)) {
                call.respond(LoginResponseRemote(token = userDTO.token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }

    }

}
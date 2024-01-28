package chunmaru.ua.features.register


import chunmaru.ua.database.users.UserDTOInsert
import chunmaru.ua.database.users.UserModel
import chunmaru.ua.features.all_users.UserResponseRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.util.*


class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val receive = UUID.randomUUID().toString()
        try {
            UserModel.insert(UserDTOInsert(receive))
            call.respond(UserResponseRemote(receive)::class)
        } catch (e: ExposedSQLException) {
            call.respond(HttpStatusCode.Conflict, "token has been used, please try again")
        }

    }

}
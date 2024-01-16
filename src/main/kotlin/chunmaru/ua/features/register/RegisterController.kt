package chunmaru.ua.features.register


import chunmaru.ua.database.tokens.TokenDTO
import chunmaru.ua.database.tokens.TokensModel
import chunmaru.ua.database.users.UserDTOInsert
import chunmaru.ua.database.users.UserModel
import chunmaru.ua.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.util.*

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {

        val receive = call.receive(RegisterReceiveRemote::class)
        if (!receive.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
        }

        val userDTO = UserModel.fetchUser(receive.login)

        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "login has been used")
        } else {

            try {
                UserModel.insert(
                    UserDTOInsert(
                        login = receive.login,
                        password = receive.password,
                        email = receive.email,
                        username = receive.username
                    )
                )
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "login has been used")
            }


            val generationToken = UUID.randomUUID().toString()
            TokensModel.insert(
                TokenDTO(
                    id = 0,
                    login = receive.login,
                    token = generationToken
                )
            )

            call.respond(RegisterReceiveResponse(generationToken))

        }


//        InMemoryCache.apply {
//            userList.add(receive)
//            token.add(TokenCache(receive.login, generationToken))
//        }
//        call.respond(RegisterReceiveResponse(generationToken))
    }

}
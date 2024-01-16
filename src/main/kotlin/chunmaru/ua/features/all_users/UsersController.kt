package chunmaru.ua.features.all_users

import chunmaru.ua.databse.admins.AdminDTO
import chunmaru.ua.databse.admins.AdminsModel
import chunmaru.ua.databse.users.UserModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class UsersController(private val call: ApplicationCall) {

    suspend fun getAllUsers() {
        val token = call.request.queryParameters["Bearer-Authorization"]
        isAdmin(token)

        val start = call.request.queryParameters["_start"]?.toIntOrNull()
        val limit = call.request.queryParameters["limit"]?.toIntOrNull()

        val users = if (start != null && limit != null) UserModel.selectAllUsers(start, limit).map {
            it.mapToUsersResponseRemote()
        }
        else UserModel.selectAllUsers().map {
            it.mapToUsersResponseRemote()
        }
        call.respond(users)

    }

    suspend fun addAdmin() {
        val token = call.request.queryParameters["Bearer-Authorization"]
        isAdmin(token)

        val receive = call.receive(UserReceiveRemote::class)
        val user = UserModel.fetchUser(receive.login)
        if (user == null)
            call.respond(HttpStatusCode.BadRequest, "Login does not exist")

        if (AdminsModel.isAdminLogin(receive.login))
            call.respond(HttpStatusCode.Conflict, "User is admin")
        else {
            AdminsModel.addAdmin(
                AdminDTO(
                    token = token!!,
                    login = receive.login
                )
            )

            call.respond("Status:Success")
        }


    }


    private suspend fun isAdmin(token: String?) {
        if (token == null || !AdminsModel.isAdminTokenValid(token)) {
            call.respond(HttpStatusCode.Conflict, "Access denied")
        }
    }


}
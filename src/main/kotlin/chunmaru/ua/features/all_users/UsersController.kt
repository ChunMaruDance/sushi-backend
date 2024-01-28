package chunmaru.ua.features.all_users

import chunmaru.ua.database.admins.AdminInsert
import chunmaru.ua.database.admins.AdminResponse
import chunmaru.ua.database.admins.AdminsModel
import chunmaru.ua.database.users.UserModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class UsersController(private val call: ApplicationCall) {

    suspend fun getAllUsers() {
        val token = call.request.queryParameters["Bearer-Authorization"]
        isAdmin(token, call)

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
        isAdmin(token, call)

        val receive = call.receive(AdminReceive::class)

        try {
            AdminsModel.addAdmin(
                AdminInsert(
                    login = receive.login,
                    password = receive.password,
                    email = receive.email,
                    username = receive.username
                )
            )

            call.respond("Status:Success")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "login used")
        }

    }

    suspend fun getAdminByToken() {
        val token = call.request.queryParameters["Bearer-Authorization"]
        if (token == null) {
            call.respond(HttpStatusCode.BadRequest, "Token does not exist")
        } else {
            val admin = AdminsModel.getAdminByToken(token)
            if (admin == null) {
                call.respond(HttpStatusCode.BadRequest, "Admin not founded")
            } else {
                call.respond(admin)
            }

        }
    }

}

suspend fun isAdmin(token: String?, call: ApplicationCall) {
    if (token == null)
        call.respond(HttpStatusCode.BadRequest, "Token does not exist")
    else if (!AdminsModel.isAdminTokenValid(token)) {
        call.respond(HttpStatusCode.Conflict, "Access denied")
    }
}
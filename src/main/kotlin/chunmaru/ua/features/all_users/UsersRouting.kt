package chunmaru.ua.features.all_users

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureUsersRouting() {
    routing {

        get("/users/all") {
            UsersController(call).getAllUsers()
        }

        post("/users/admin/add") {
            UsersController(call).addAdmin()
        }

        get("users/admin/info"){
            UsersController(call).getAdminByToken()
        }


    }
}
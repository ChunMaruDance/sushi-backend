package chunmaru.ua.features.all_users

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureUsersRouting() {
    routing {

        get("/users/all") {
            val controller = UsersController(call)
            controller.getAllUsers()
        }

        post("/users/add") {
            val controller = UsersController(call)
            controller.addAdmin()
        }

    }
}
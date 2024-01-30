package chunmaru.ua

import chunmaru.ua.features.all_users.configureUsersRouting
import chunmaru.ua.features.category.configureCategoryRouting
import chunmaru.ua.features.dishes.configureDishesRouting
import chunmaru.ua.features.ingredients.configureIngredientsRouting
import chunmaru.ua.features.login.configureLoginRouting
import chunmaru.ua.features.register.configureRegisterRouting
import chunmaru.ua.plugins.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database

fun main() {
    databaseConfigure()
    embeddedServer(CIO, port = 8080, host = "", module = Application::module) //todo host
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureSerialization()

    //routing
    configureDishesRouting()
    configureIngredientsRouting()
    configureLoginRouting()
    configureRegisterRouting()
    configureUsersRouting()
    configureCategoryRouting()

}

fun databaseConfigure() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/sushi",
        driver = "org.postgresql.Driver",
        user = "postgres", password = "" // todo password
    )
}


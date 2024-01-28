package chunmaru.ua.features.category

import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureCategoryRouting() {

    routing {

        post("/dishes/categories/add") {
         //   CategoryController(call = call)
        }

        get("/dishes/categories/get"){
            CategoryController(call = call).getCategories()
        }

    }
}
package chunmaru.ua.features.ingredients

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureIngredientsRouting() {

    routing {

        get("/dishes/ingredients") {
            IngredientsController(call = call).getAllIngredients()
        }

        post("/dishes/ingredients/add") {
            IngredientsController(call = call).addIngredient()
        }

        get("/dishes/ingredients/dish") {
            IngredientsController(call = call).getIngredientsByDish()
        }


    }

}
package chunmaru.ua.features.dishes


import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureDishesRouting() {
    routing {

        post("/dishes/add") {
            DishesController(call = call).addDish()
        }

        get("/dishes/dish") {
            DishesController(call = call).getDish()
        }

        get("/dishes/by_category") {
            DishesController(call = call).getDishesByCategory()
        }

        get("/dishes/special") {
            DishesController(call =call).getSpecialDish()
        }


    }

}

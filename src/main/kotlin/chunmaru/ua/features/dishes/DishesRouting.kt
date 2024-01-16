package chunmaru.ua.features.dishes

import chunmaru.ua.databse.dishes.DishesDTO
import chunmaru.ua.databse.dishes.DishesModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureDishesRouting() {
    routing {
        post("/dishes/add") {

            val receive = call.receive(DishesAddReceiveRemote::class)
            DishesModel.addDish(
                DishesDTO(
                    id = 0,
                    name = receive.dishes.name,
                    descriptions = receive.dishes.descriptions,
                    price = receive.dishes.price,
                    discount = receive.dishes.discount,
                    weight = receive.dishes.weight,
                    image = receive.byteArray
                )
            )
            call.respond("gd")

        }

        get("/dishes/dish") {

            val receive = call.parameters["id"]
            receive?.let {
                try {
                    val dish = DishesModel.getDishById(it.toInt())
                    call.respond(
                        DishResponseRemote(
                            id = dish.id,
                            name = dish.name,
                            descriptions = dish.descriptions,
                            price = dish.price,
                            weight = dish.weight,
                            image = dish.image,
                            discount = dish.discount
                        )
                    )
                } catch (e: Exception) {
                    print("Error $e")
                    call.respond(HttpStatusCode.BadRequest, "Error :$e")
                }

            } ?: call.respond("Not Valid parameters")


        }


    }

}

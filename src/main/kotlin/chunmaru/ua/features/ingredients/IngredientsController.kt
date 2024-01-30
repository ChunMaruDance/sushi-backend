package chunmaru.ua.features.ingredients

import chunmaru.ua.database.dish_ingredients.DishIngredientsModel
import chunmaru.ua.database.ingredients.IngredientsModel
import chunmaru.ua.features.all_users.isAdmin
import chunmaru.ua.features.dishes.DishesAddReceiveRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class IngredientsController(
    private val call: ApplicationCall
) {

    suspend fun getAllIngredients() {
        val ingredients = IngredientsModel.getAllIngredients()
        call.respond(ingredients.toIngredientsResponse())
    }

    suspend fun addIngredient() {
        val token = call.request.queryParameters["Bearer-Authorization"]
        isAdmin(token, call)

        val receive = call.receive(IngredientReceiveRemote::class)
        IngredientsModel.addIngredient(
            receive.toIngredientDTO()
        )

    }

    suspend fun getIngredientsByDish() {

        val dishId = call.request.queryParameters["dishId"]

        if (dishId != null) {
            val ingredientsId = DishIngredientsModel.getIngredientsByDishId(dishId.toInt())
            val ingredients = IngredientsModel.getIngredientsByIds(ingredientsId)
            call.respond(ingredients.toIngredientsResponse())
        } else {
            call.respond(HttpStatusCode.BadRequest, "dishId not exist")
        }


    }


}
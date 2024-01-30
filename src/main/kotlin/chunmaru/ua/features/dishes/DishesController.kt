package chunmaru.ua.features.dishes

import chunmaru.ua.database.dish_categories.DishCategoriesModel
import chunmaru.ua.database.dish_category_association.DishCategoryAssociationDTO
import chunmaru.ua.database.dish_category_association.DishCategoryAssociationModel
import chunmaru.ua.database.dish_ingredients.DishIngredientsModel
import chunmaru.ua.database.dishes.DishesModel
import chunmaru.ua.database.dishes.DishesModel.searchDishesByQuery
import chunmaru.ua.database.dishes.toDishResponse
import chunmaru.ua.database.special_dish.SpecialDishModel
import chunmaru.ua.features.all_users.isAdmin
import chunmaru.ua.utils.toDishResponseRemote
import chunmaru.ua.utils.toDishesDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class DishesController(private val call: ApplicationCall) {

    suspend fun addDish() {
        val token = call.request.queryParameters["Bearer-Authorization"]
        isAdmin(token, call)

        val receive = call.receive(DishesAddReceiveRemote::class)

        val categoryId = DishCategoriesModel.getIdByName(receive.dish.category)
        if (categoryId == null) {
            call.respond(HttpStatusCode.BadRequest, "Category does not exist")
        } else {
            DishesModel.addDish(receive.toDishesDTO())
            val dishId = DishesModel.getDishIdByName(receive.dish.name)

            DishCategoryAssociationModel.addDishToCategory(
                DishCategoryAssociationDTO(
                    dishId = dishId!!,
                    categoryId = categoryId
                )
            )
            DishIngredientsModel.addIngredientsToDish(dishId, receive.ingredients)

        }

    }

    suspend fun getDish() {
        val receive = call.parameters["id"]
        receive?.let {
            try {
                val dish = DishesModel.getDishById(it.toInt())
                call.respond(dish.toDishResponseRemote())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error : id not founded")
            }

        } ?: call.respond("Not Valid parameters")
    }


    suspend fun getDishesByCategory() {
        val category = call.parameters["category"]

        if (category != null) {

            try {
                val dishes = DishCategoryAssociationModel.getDishesByCategory(category)
                call.respond(dishes)
            } catch (_: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Category does not exist")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Category parameter is missing")
        }

    }

    suspend fun getSpecialDish() {
        val dish = SpecialDishModel.getSpecialDish()
        if (dish != null) {
            call.respond(dish.toDishResponseRemote())
        } else {
            call.respondText("Special dish not found", status = HttpStatusCode.NotFound)
        }
    }

    suspend fun getSearchQuery() {
        val query = call.parameters["query"]

        if (query != null) {
            val dishes = searchDishesByQuery(query)
            call.respond(dishes.toDishResponse())
        } else {
            call.respond(HttpStatusCode.BadRequest, "query not exist")
        }

    }

}
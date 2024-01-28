package chunmaru.ua.features.category

import chunmaru.ua.database.dish_categories.CategoryDTO.Companion.toCategoryReceiveResponseList
import chunmaru.ua.database.dish_categories.DishCategoriesModel
import io.ktor.server.application.*
import io.ktor.server.response.*

class CategoryController(private val call: ApplicationCall) {

    suspend fun getCategories() {
        val categories = DishCategoriesModel.getAllCategories()
        call.respond(categories.toCategoryReceiveResponseList())

    }


}
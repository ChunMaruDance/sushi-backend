package chunmaru.ua.database.dish_ingredients

import chunmaru.ua.database.dishes.DishesModel
import chunmaru.ua.database.ingredients.IngredientsModel
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction


object DishIngredientsModel : Table("dish_ingredients") {

    val dishId = integer("dish_id").references(DishesModel.id)
    val ingredientId = integer("ingredient_id").references(IngredientsModel.id)

    override val primaryKey = PrimaryKey(dishId, ingredientId, name = "PK_DishIngredients")

    fun addIngredientToDish(dishIngredientsDTO: DishIngredientsDTO) {
        transaction {
            DishIngredientsModel.insert {
                it[dishId] = dishIngredientsDTO.dishId
                it[ingredientId] = dishIngredientsDTO.ingredientId
            }
        }

    }

    fun removeIngredientsByDishId(ingredientId: Int) {
        transaction {
            DishIngredientsModel.deleteWhere { DishIngredientsModel.ingredientId eq ingredientId }
        }
    }

    fun removeDish(dishId: Int) {
        transaction {
            DishIngredientsModel.deleteWhere { DishIngredientsModel.dishId eq dishId }
        }
    }

    fun getIngredientsByDishId(dishId: Int): List<Int> {
        return DishIngredientsModel.select { DishIngredientsModel.dishId eq dishId }
            .map { it[ingredientId] }
    }

    fun addIngredientsToDish(dishId: Int, ingredientsId: List<Int>) {
        transaction {
            ingredientsId.forEach { _ ->
                DishIngredientsModel.insert {
                    it[DishIngredientsModel.dishId] = dishId
                    it[ingredientId] = ingredientId
                }
            }
        }
    }


}
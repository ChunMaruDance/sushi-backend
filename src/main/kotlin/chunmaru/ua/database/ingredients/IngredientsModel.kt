package chunmaru.ua.database.ingredients

import chunmaru.ua.database.dishes.DishesModel
import org.jetbrains.exposed.sql.Table

object IngredientsModel : Table("ingredient") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val description = text("description")
    val image = blob("image")

}


object DishIngredientsModel : Table("dish_ingredients"){

    val dishId = integer("dish_id").references(DishesModel.id)
    val ingredientId = integer("ingredient_id").references(IngredientsModel.id)

    override val primaryKey = PrimaryKey(dishId, ingredientId, name = "PK_DishIngredients")

}



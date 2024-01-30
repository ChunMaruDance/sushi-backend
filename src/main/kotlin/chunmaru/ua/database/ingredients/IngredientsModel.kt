package chunmaru.ua.database.ingredients

import chunmaru.ua.database.dishes.DishesModel
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.transactions.transaction

object IngredientsModel : Table("ingredient") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val description = text("description")
    val image = blob("image")


    fun addIngredient(ingredientsDTO: IngredientsDTO) {
        transaction {
            IngredientsModel
                .insert {
                    it[name] = ingredientsDTO.name
                    it[description] = ingredientsDTO.description
                    it[image] = ExposedBlob(ingredientsDTO.image)
                }
        }

    }

    fun getAllIngredients(): List<IngredientsDTO> {
        return transaction {
            IngredientsModel.selectAll().map { row ->
                IngredientsDTO(
                    id = row[IngredientsModel.id],
                    name = row[name],
                    description = row[description],
                    image = row[image].bytes
                )

            }
        }

    }

    fun getIngredientsByIds(ids: List<Int>): List<IngredientsDTO> {
        return transaction {
            IngredientsModel
                .select { IngredientsModel.id inList ids }
                .map {
                    IngredientsDTO(
                        id = it[IngredientsModel.id],
                        name = it[name],
                        description = it[description],
                        image = it[image].bytes
                    )
                }
        }
    }


    fun getIngredientById(ingredientId: Int): IngredientsDTO? {
        return transaction {
            IngredientsModel
                .select { IngredientsModel.id eq ingredientId }
                .singleOrNull()
                ?.let {
                    IngredientsDTO(
                        id = it[IngredientsModel.id],
                        name = it[name],
                        description = it[description],
                        image = it[image].bytes
                    )
                }
        }

    }

}




package chunmaru.ua.database.dish_categories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DishCategoriesModel : Table("dish_categories") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val descriptions = text("descriptions")

    fun addCategory(category: CategoryDTO) {
        transaction {
            insert {
                it[name] = category.name
                it[descriptions] = category.descriptions
            }
        }
    }

    fun removeCategory(categoryName: String) {
        transaction {
            deleteWhere { name eq categoryName }
        }
    }

    fun getAllCategories(): List<CategoryDTO> {
        return transaction {
            selectAll().map { row ->
                CategoryDTO(
                    id = id,
                    name = row[name],
                    descriptions = row[descriptions]
                )
            }
        }
    }

    fun getIdByName(categoryName: String): Int? {
        return try {
            transaction {
                val categoryModel = DishCategoriesModel.select { name eq categoryName }.single()
                categoryModel[DishCategoriesModel.id]

            }
        } catch (e: Exception) {
            null
        }

    }


}
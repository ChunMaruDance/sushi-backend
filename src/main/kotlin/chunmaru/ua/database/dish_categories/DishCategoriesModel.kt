package chunmaru.ua.database.dish_categories

import chunmaru.ua.database.dish_category_association.DishCategoryDTO
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object DishCategoriesModel : Table("dish_categories") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)

    fun addCategory(categoryName: String) {
        transaction {
            insert {
                it[name] = categoryName
            }
        }
    }

    fun removeCategory(categoryId: Int) {
        transaction {
            deleteWhere { DishCategoriesModel.id eq categoryId }
        }
    }

    fun getAllCategories(): List<DishCategoryDTO> {
        return transaction {
            selectAll().map { row ->
                DishCategoryDTO(
                    row[DishCategoriesModel.id],
                    row[name]
                )
            }
        }
    }

}
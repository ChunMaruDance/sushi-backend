package chunmaru.ua.database.dish_category_association

import chunmaru.ua.database.dish_categories.DishCategoriesModel
import chunmaru.ua.database.dishes.DishesDTO
import chunmaru.ua.database.dishes.DishesModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DishCategoryAssociationModel : Table("dish_category_association") {
    private val dishId = integer("dish_id").references(DishesModel.id)
    private val categoryId = integer("category_id").references(DishCategoriesModel.id)

    override val primaryKey = PrimaryKey(dishId, categoryId, name = "PK_DishCategoryAssociation")

    fun addDishToCategory(dishCategoryAssociationDTO: DishCategoryAssociationDTO) {
        transaction {
            DishCategoryAssociationModel.insert {
                it[this.dishId] = dishCategoryAssociationDTO.dishId
                it[this.categoryId] = dishCategoryAssociationDTO.categoryId
            }
        }
    }


    fun removeDishFromCategory(dishCategoryAssociationDTO: DishCategoryAssociationDTO) {
        transaction {
            DishCategoryAssociationModel
                .deleteWhere {
                    dishId eq dishCategoryAssociationDTO.dishId and (categoryId eq dishCategoryAssociationDTO.categoryId)
                }
        }
    }


    private fun getDishesByCategory(category: Int): List<DishesDTO> {
        return transaction {
            DishCategoryAssociationModel
                .slice(dishId)
                .select { categoryId eq category }
                .mapNotNull { row ->
                    val dishId = row[DishCategoryAssociationModel.dishId]
                    DishesModel
                        .select { DishesModel.id eq dishId }
                        .singleOrNull()?.let { dishesRow ->
                            DishesDTO(
                                id = dishesRow[DishesModel.id],
                                name = dishesRow[DishesModel.name],
                                descriptions = dishesRow[DishesModel.description],
                                price = dishesRow[DishesModel.price],
                                discount = dishesRow[DishesModel.discount],
                                weight = dishesRow[DishesModel.weight],
                                image = dishesRow[DishesModel.image].bytes
                            )
                        }
                }
        }
    }


    fun getDishesByCategory(category: String): List<DishesDTO> {

        return transaction {
            val id = DishCategoriesModel.getIdByName(category)!!
            getDishesByCategory(id)

        }

    }


}
package chunmaru.ua.database.special_dish

import chunmaru.ua.database.dishes.DishesDTO
import chunmaru.ua.database.dishes.DishesModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object SpecialDishModel : Table("special_dish") {

    private val id = integer("id")
    private val discount = float("discount")


    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id, discount)

    fun insert(specialDishDTO: SpecialDishDTO) {
        transaction {
            SpecialDishModel.insert {
                it[id] = specialDishDTO.id
                it[discount] = specialDishDTO.discount
            }
        }
    }

    fun deleteSpecialDish(id: Int) {
        transaction {
            SpecialDishModel.deleteWhere { SpecialDishModel.id eq id }
        }

    }


    fun getSpecialDish(): DishesDTO? {
        return transaction {

            val specialDish = SpecialDishModel.selectAll().singleOrNull() ?: return@transaction null
            val modelDish = DishesModel.select { DishesModel.id eq specialDish[SpecialDishModel.id] }.singleOrNull()
                ?: return@transaction null

            DishesDTO(
                id = modelDish[DishesModel.id],
                name = modelDish[DishesModel.name],
                descriptions = modelDish[DishesModel.description],
                price = modelDish[DishesModel.price],
                weight = modelDish[DishesModel.weight],
                image = modelDish[DishesModel.image].bytes,
                discount = specialDish[discount]
            )

        }
    }


}
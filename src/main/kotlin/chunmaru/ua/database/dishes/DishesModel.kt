package chunmaru.ua.database.dishes

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.transactions.transaction

object DishesModel : Table("dishes") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val description = text("description")
    val price = float("price")
    val discount = float("discount").default(0f)
    val weight = float("weight")
    val image = blob("image")

    fun addDish(dishesDTO: DishesDTO) {
        transaction {
            insert {
                it[name] = dishesDTO.name
                it[description] = dishesDTO.descriptions
                it[price] = dishesDTO.price
                it[discount] = dishesDTO.discount
                it[weight] = dishesDTO.weight
                it[image] = ExposedBlob(dishesDTO.image)
            }
        }
    }

    fun removeDish(dishId: Int) {
        transaction {
            deleteWhere { DishesModel.id eq dishId }
        }
    }

    fun getAllDishes(): List<DishesDTO> {
        return transaction {
            selectAll().map { row ->
                DishesDTO(
                    row[DishesModel.id],
                    row[name],
                    row[description],
                    row[price],
                    row[discount],
                    row[weight],
                    row[image].bytes
                )
            }
        }
    }

    fun getDishById(dishId: Int): DishesDTO {
        return transaction {
            val dishesModel = select { DishesModel.id.eq(dishId) }.single()
            DishesDTO(
                id = dishesModel[DishesModel.id],
                name = dishesModel[name],
                descriptions = dishesModel[description],
                price = dishesModel[price],
                discount = dishesModel[discount],
                weight = dishesModel[weight],
                image = dishesModel[image].bytes
            )
        }

    }


}
package chunmaru.ua.database.dishes

import chunmaru.ua.features.dishes.DishResponseRemote


class DishesDTO(
    val id: Int,
    val name: String,
    val descriptions: String,
    val price: Float,
    val discount: Float,
    val weight: Float,
    val image: ByteArray
)


fun List<DishesDTO>.toDishResponse(): List<DishResponseRemote> {
    return this.map {
        DishResponseRemote(
            id = it.id,
            name = it.name,
            descriptions = it.descriptions,
            discount = it.discount,
            image = it.image,
            weight = it.weight,
            price = it.price
        )
    }
}


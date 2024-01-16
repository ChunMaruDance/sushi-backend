package chunmaru.ua.databse.dishes


class DishesDTO(
    val id: Int,
    val name: String,
    val descriptions: String,
    val price: Float,
    val discount: Float,
    val weight: Float,
    val image: ByteArray
)

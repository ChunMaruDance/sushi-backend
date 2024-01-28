package chunmaru.ua.features.dishes

import kotlinx.serialization.Serializable

@Serializable
data class DishesAddReceiveRemote(
    val category: String,
    val dishes: DishesReceiveRemote,
    val byteArray: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DishesAddReceiveRemote

        if (dishes != other.dishes) return false
        if (!byteArray.contentEquals(other.byteArray)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dishes.hashCode()
        result = 31 * result + byteArray.contentHashCode()
        return result
    }
}

@Serializable
data class DishesReceiveRemote(
    val id: Int,
    val name: String,
    val descriptions: String,
    val price: Float,
    val discount: Float,
    val weight: Float,
    val category: String
)

@Serializable
class DishResponseRemote(
    val id: Int,
    val name: String,
    val descriptions: String,
    val price: Float,
    val discount: Float,
    val weight: Float,
    val image: ByteArray
)

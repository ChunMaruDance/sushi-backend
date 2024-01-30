package chunmaru.ua.features.ingredients

import chunmaru.ua.database.ingredients.IngredientsDTO
import kotlinx.serialization.Serializable


@Serializable
class IngredientsResponseRemote(
    val id: Int,
    val name: String,
    val description: String,
    val image: ByteArray
)

fun List<IngredientsDTO>.toIngredientsResponse(): List<IngredientsResponseRemote> {
    return map {
        IngredientsResponseRemote(
            id = it.id,
            name = it.name,
            description = it.description,
            image = it.image
        )
    }
}

fun IngredientReceiveRemote.toIngredientDTO(): IngredientsDTO {
    return IngredientsDTO(
        name = name,
        id = id,
        description = description,
        image = image
    )
}


@Serializable
data class IngredientReceiveRemote(
    val id: Int,
    val name: String,
    val description: String,
    val image: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IngredientReceiveRemote

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }

}
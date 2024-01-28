package chunmaru.ua.features.category

import kotlinx.serialization.Serializable


@Serializable
class CategoryReceiveResponse(
    val id: String,
    val name: String,
    val descriptions: String,
)
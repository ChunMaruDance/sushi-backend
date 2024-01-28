package chunmaru.ua.database.dish_categories

import chunmaru.ua.features.category.CategoryReceiveResponse

data class CategoryDTO(
    val id: String,
    val name: String,
    val descriptions: String,
) {


    fun toCategoryReceiveResponse(): CategoryReceiveResponse =
        CategoryReceiveResponse(
            id = id,
            name = name,
            descriptions = descriptions
        )


    companion object {

        fun List<CategoryDTO>.toCategoryReceiveResponseList(): List<CategoryReceiveResponse> {
            return map { it.toCategoryReceiveResponse() }
        }

    }

}
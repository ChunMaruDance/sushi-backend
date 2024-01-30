package chunmaru.ua.utils

import chunmaru.ua.database.dishes.DishesDTO
import chunmaru.ua.features.dishes.DishResponseRemote
import chunmaru.ua.features.dishes.DishesAddReceiveRemote

fun DishesAddReceiveRemote.toDishesDTO(): DishesDTO =
    DishesDTO(
        id = 0,
        name = this.dish.name,
        descriptions = this.dish.descriptions,
        price = this.dish.price,
        discount = this.dish.discount,
        weight = this.dish.weight,
        image = this.byteArray
    )

fun DishesDTO.toDishResponseRemote(): DishResponseRemote =
    DishResponseRemote(
        id = this.id,
        name = this.name,
        descriptions = this.descriptions,
        price = this.price,
        weight = this.weight,
        image = this.image,
        discount = this.discount
    )


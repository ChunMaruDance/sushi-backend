package chunmaru.ua.database.admins

import kotlinx.serialization.Serializable

class AdminInsert(
    val login:String,
    val username:String,
    val email:String,
    val password:String
)

data class AdminDTO(
    val login:String,
    val username:String,
    val email:String,
    val passwordSalt:String,
    val passwordHash:String,
    val token:String
)

@Serializable
data class AdminResponse(
    val login:String,
    val username:String,
    val email:String,
)



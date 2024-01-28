package chunmaru.ua.features.all_users

import kotlinx.serialization.Serializable


@Serializable
class UserResponseRemote(
    val token: String
)

@Serializable
data class AdminReceive(
    val login: String,
    val password: String,
    val username: String,
    val email: String
)

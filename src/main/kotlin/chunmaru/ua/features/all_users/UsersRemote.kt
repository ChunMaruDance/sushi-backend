package chunmaru.ua.features.all_users

import kotlinx.serialization.Serializable


@Serializable
class UserResponseRemote(
    val login: String,
    val email: String?,
    val username: String
)

@Serializable
data class UserReceiveRemote(
    val login: String
)

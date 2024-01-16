package chunmaru.ua.features.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterReceiveRemote(
    val login: String,
    val email: String,
    val password: String,
    val username: String
)

@Serializable
data class RegisterReceiveResponse(
    val token: String
)


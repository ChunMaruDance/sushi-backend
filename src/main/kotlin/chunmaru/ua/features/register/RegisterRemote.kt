package chunmaru.ua.features.register

import kotlinx.serialization.Serializable



@Serializable
data class RegisterReceiveResponse(
    val token: String
)


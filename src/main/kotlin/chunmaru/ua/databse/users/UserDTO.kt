package chunmaru.ua.databse.users

import chunmaru.ua.features.all_users.UserResponseRemote

class UserDTO(
    val login: String,
    val email: String?,
    val username: String,
    val passwordHash: String,
    val passwordSalt: String
)

class UserDTOInsert(
    val login: String,
    val email: String?,
    val username: String,
    val password: String
)


class UserDTOResponse(
    val login: String,
    val email: String?,
    val username: String
) {
    fun mapToUsersResponseRemote(): UserResponseRemote =
        UserResponseRemote(
            username = username,
            login = login,
            email = email
        )
}

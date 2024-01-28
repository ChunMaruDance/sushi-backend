package chunmaru.ua.database.users

import chunmaru.ua.features.all_users.UserResponseRemote

class UserDTO(
  val  token:String
//    val login: String,
//    val email: String?,
//    val username: String,
//    val passwordHash: String,
//    val passwordSalt: String
)

class UserDTOInsert(
   val  token:String
//    val login: String,
//    val email: String?,
//    val username: String,
//    val password: String
)


class UserDTOResponse(
   val  token:String
//    val login: String,
//    val email: String?,
//    val username: String
) {
    fun mapToUsersResponseRemote(): UserResponseRemote =
        UserResponseRemote(
            token = token,
//            username = username,
//            login = login,
//            email = email
        )
}

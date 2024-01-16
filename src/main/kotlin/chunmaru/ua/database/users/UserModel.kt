package chunmaru.ua.database.users

import chunmaru.ua.utils.PasswordUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object UserModel : Table("users") {

    private val login = UserModel.varchar("login", 25).uniqueIndex()
    private val username = UserModel.varchar("username", 50)
    private val email = UserModel.varchar("email", 50).nullable()
    private val passwordHash = UserModel.varchar("password_hash", 64)
    private val passwordSalt = UserModel.varchar("password_salt", 64)


    fun insert(userDTO: UserDTOInsert) {
        transaction {
            val salt = PasswordUtils.generateSecureRandomSalt()
            UserModel.insert {
                it[login] = userDTO.login
                it[passwordHash] = PasswordUtils.hashPassword(userDTO.password, salt)
                it[passwordSalt] = salt
                it[email] = userDTO.email
                it[username] = userDTO.username
            }
        }
    }

    fun fetchUser(login: String): UserDTO? {
        return try {
            transaction {
                val userModel = UserModel.select { UserModel.login.eq(login) }.single()
                UserDTO(
                    login = userModel[UserModel.login],
                    username = userModel[username],
                    email = userModel[email],
                    passwordHash = userModel[passwordHash],
                    passwordSalt = userModel[passwordSalt]
                )
            }

        } catch (e: Exception) {
            null
        }

    }


    fun selectAllUsers(): List<UserDTOResponse> {
        return try {

            transaction {
                UserModel.selectAll().map {
                    UserDTOResponse(
                        login = it[login],
                        username = it[username],
                        email = it[email]
                    )
                }
            }
        } catch (e: Exception) {
            listOf()
        }
    }


    fun selectAllUsers(start: Int, limit: Int): List<UserDTOResponse> {
        return try {
            val offset = ((start - 1) * limit).toLong()
            transaction {
                UserModel.selectAll().limit(limit, offset).map {
                    UserDTOResponse(
                        login = it[login],
                        username = it[username],
                        email = it[email]
                    )
                }
            }
        } catch (e: Exception) {
            listOf()
        }
    }


}
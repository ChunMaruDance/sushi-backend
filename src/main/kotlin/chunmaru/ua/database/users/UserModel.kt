package chunmaru.ua.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object UserModel : Table("users") {

    private val token = UserModel.varchar("token", 150)

    fun insert(userDTO: UserDTOInsert) {
        transaction {

            UserModel.insert {
                it[token] = userDTO.token
            }
        }
    }

    fun selectAllUsers(): List<UserDTOResponse> {
        return try {

            transaction {
                UserModel.selectAll().map {
                    UserDTOResponse(
                        token = it[token]
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
                        token = it[token]
                    )
                }
            }
        } catch (e: Exception) {
            listOf()
        }
    }


}
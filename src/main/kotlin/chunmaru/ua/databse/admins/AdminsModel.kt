package chunmaru.ua.databse.admins

import chunmaru.ua.databse.tokens.TokensModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object AdminsModel : Table("admins") {
    private val id = integer("id").autoIncrement()
    private val login = varchar("login", 25).uniqueIndex()
    private val token = varchar("token", 50)


    fun addAdmin(adminDTO: AdminDTO) {
        val userTokens = TokensModel.fetchTokens(adminDTO.login)
        transaction {
            userTokens.forEach { tokenDTO ->
                AdminsModel.insert {
                    it[token] = tokenDTO.token
                    it[login] = tokenDTO.login
                }
            }

        }
    }

    fun isAdminTokenValid(adminToken: String): Boolean {
        return runCatching {
            transaction {
                AdminsModel.select { token eq adminToken }.count() > 0
            }
        }.getOrElse { false }
    }

    fun isAdminLogin(userLogin: String): Boolean {
        return transaction {
            AdminsModel.select { login eq userLogin }.count() > 0
        }
    }


}
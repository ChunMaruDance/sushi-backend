package chunmaru.ua.database.admins


import chunmaru.ua.utils.PasswordUtils
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object AdminsModel : Table("admins") {

    private val login = varchar("login", 50).uniqueIndex()
    private val username = varchar("username", 75)
    private val email = varchar("email", 70)
    private val passwordHash = varchar("password_hash", 150)
    private val passwordSalt = varchar("password_salt", 150)
    private val token = varchar("token", 150)

    fun addAdmin(adminDTO: AdminInsert) {
        transaction {
            val salt = PasswordUtils.generateSecureRandomSalt()
            AdminsModel.insert {
                it[username] = adminDTO.username
                it[login] = adminDTO.login
                it[email] = adminDTO.email
                it[passwordHash] = PasswordUtils.hashPassword(adminDTO.password, salt)
                it[passwordSalt] = salt
                it[token] = UUID.randomUUID().toString()
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


    fun getAdminByLogin(login: String): AdminDTO? {
        return try {
            transaction {
                val model = AdminsModel.select { AdminsModel.login eq login }.single()
                AdminDTO(
                    login = model[AdminsModel.login],
                    username = model[username],
                    email = model[email],
                    passwordHash = model[passwordHash],
                    passwordSalt = model[passwordSalt],
                    token = model[token]
                )
            }
        } catch (e: Exception) {
            null
        }

    }

    fun getAdminByToken(token: String): AdminResponse? {
        return try {
            transaction {

                val model = AdminsModel.select { AdminsModel.token eq token }.single()
                AdminResponse(
                    login = model[login],
                    username = model[username],
                    email = model[email]
                )
            }
        } catch (e: Exception) {
            null
        }


    }

}

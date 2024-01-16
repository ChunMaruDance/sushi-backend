package chunmaru.ua.databse.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object TokensModel : Table("tokens") {
    private val id = TokensModel.integer("id").autoIncrement()
    private val login = TokensModel.varchar("login", 25)
    private val token = TokensModel.varchar("token", 50)


    fun insert(tokenDTO: TokenDTO) {
        transaction {
            TokensModel.insert {
                it[login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }

    fun fetchToken(login: String): TokenDTO {
        val tokenModel = TokensModel.select { TokensModel.login.eq(login) }.single()
        return TokenDTO(
            id = tokenModel[id],
            token = tokenModel[token],
            login = tokenModel[TokensModel.login]
        )

    }

    fun fetchTokens(login: String): List<TokenDTO> {
        return transaction {
            TokensModel
                .select { TokensModel.login eq login }
                .map {
                    TokenDTO(
                        id = it[TokensModel.id],
                        token = it[token],
                        login = it[TokensModel.login]
                    )
                }
        }
    }


}
package chunmaru.ua.utils

import org.mindrot.jbcrypt.BCrypt
import java.security.SecureRandom

class PasswordUtils {
    companion object {

        fun hashPassword(password: String, salt: String): String =
            BCrypt.hashpw(password + salt, BCrypt.gensalt())

        fun verifyPassword(plainPassword: String, hashedPassword: String, salt: String): Boolean =
            BCrypt.checkpw(plainPassword + salt, hashedPassword)

        fun generateSecureRandomSalt(): String {
            val secureRandom = SecureRandom()
            val salt = ByteArray(16)
            secureRandom.nextBytes(salt)
            return BCrypt.gensalt(12, secureRandom)
        }
    }
}
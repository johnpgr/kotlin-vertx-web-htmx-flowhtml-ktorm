package experiment.utils

import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.KeyStoreOptions
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.auth.jwt.JWTAuthOptions
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.kotlin.coroutines.coAwait

object JWTManager {
    private lateinit var provider: JWTAuth

    fun init(vertx: Vertx) {
        val config = JWTAuthOptions()
            .setKeyStore(KeyStoreOptions()
                .setType("jceks")
                .setPath("keystore.jceks")
                .setPassword("secret"))

        provider = JWTAuth.create(vertx, config)
    }

    fun generateToken(userId: String): String {
        return provider.generateToken(
            json {
                obj(
                    "sub" to userId,
                    "iat" to System.currentTimeMillis() / 1000
                )
            }
        )
    }

    /**
     * @return userId: String
     */
    suspend fun verifyToken(token: String): String? {
        return try {
            val result = provider.authenticate(JsonObject().put("token", token)).coAwait()
            result.principal().getString("sub")
        } catch (e: Exception) {
            null
        }
    }
}

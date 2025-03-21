package org.example.blogmultiplatform.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.data.MongoDB
import org.example.blogmultiplatform.models.User
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import org.example.blogmultiplatform.models.UserWithoutPassword
import org.example.blogmultiplatform.util.MongoDBClient
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

@Serializable
data class ErrorResponse(val message: String)

@Api(routeOverride = "usercheck")
suspend fun userCheck(context: ApiContext) {
    try {
        println("entrez 2")
        val userRequest = context.req.body?.decodeToString()?.let { Json.decodeFromString<User>(it) }

        val user = userRequest?.let{
            context.data.getValue<MongoDB>().checkUserExistence(
                User(username = it.username, password = hashPassword(it.password))
            )
        }

        if (user != null){
            context.res.setBodyText(
                Json.encodeToString(
                    UserWithoutPassword(id = user.id!!, username = user.username)
                )
            )
        }else {
            context.res.setBodyText(Json.encodeToString(ErrorResponse("User n'existe pas ${userRequest?.username} : ${userRequest?.password}" )))
        }
    }catch (e : Exception){
        context.res.setBodyText(Json.encodeToString(ErrorResponse("Erreur vien de userCheck : ${e.message}")))
    }
}

@Api(routeOverride = "test")
suspend fun testApi(context: ApiContext) {
    try {
        println("Requête test reçue")
        context.res.setBodyText("L'API fonctionne correctement !")
    } catch (e: Exception) {
        context.res.setBodyText("Erreur lors du test: ${e.message}")
    }
}


@Api(routeOverride = "add-user")
suspend fun addUser(context: ApiContext) {
    try {
        val userRequest = context.req.body?.decodeToString()?.let { Json.decodeFromString<User>(it) }

        if (userRequest != null) {
            val isInserted = context.data.getValue<MongoDB>().insertUser(userRequest)

            if (isInserted) {
                context.res.setBodyText(Json.encodeToString(mapOf("message" to "Utilisateur ajouté avec succès")))
            } else {
                context.res.setBodyText(Json.encodeToString(ErrorResponse("Échec de l'ajout de l'utilisateur")))
            }
        } else {
            context.res.setBodyText(Json.encodeToString(ErrorResponse("Requête invalide ${userRequest?.username}")))
        }
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(ErrorResponse("Erreur lors de l'ajout de l'utilisateur : ${e.message}")))
    }
}

@Api(routeOverride = "check-db-connection")
suspend fun checkDatabaseConnection(context: ApiContext) {
    try {
        // Tente de récupérer une collection de test (par exemple, "users")
        val result = MongoDBClient.usersCollection.find().limit(1).toList()

        if (result.isNotEmpty()) {
            context.res.setBodyText(Json.encodeToString(mapOf("message" to "Connexion à la base de données réussie")))
        } else {
            context.res.setBodyText(Json.encodeToString(mapOf("message" to "La base de données est connectée mais la collection est vide")))
        }
    } catch (e: Exception) {
        // Si une exception est lancée, cela signifie qu'il y a un problème avec la connexion
        context.res.setBodyText(Json.encodeToString(mapOf("error" to "Erreur de connexion à la base de données", "message" to e.message)))
    }
}




private fun hashPassword(password: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashBytes = messageDigest.digest(password.toByteArray(StandardCharsets.UTF_8))
    val hexString = StringBuffer()

    for (byte in hashBytes) {
        hexString.append(String.format("%02x", byte))
    }

    return hexString.toString()
}
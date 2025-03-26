package org.example.blogmultiplatform.api

import com.mongodb.client.model.Filters
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
import org.bson.types.ObjectId
import org.example.blogmultiplatform.models.UserWithoutPassword
import org.example.blogmultiplatform.util.MongoDBClient
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

@Serializable
data class ErrorResponse(val message: String)

@Api(routeOverride = "usercheck")
suspend fun userCheck(context: ApiContext) {
    try {

        val userRequest = context.req.body?.decodeToString()?.let { Json.decodeFromString<User>(it) }
        val filter = Filters.and(
            Filters.eq("username", userRequest?.username),
            Filters.eq("password", hashPassword(userRequest?.password))
        )
        val user =MongoDBClient.usersCollection
            .find(
                filter
            ).firstOrNull()


        if (user != null){
            context.res.setBodyText(
                Json.encodeToString(
                    UserWithoutPassword(
                        id = user["_id"].toString(),
                        username = user["username"]?.toString() ?: "",
                    )
                )
            )
        }else {
            context.res.setBodyText(Json.encodeToString(ErrorResponse("User n'existe pas ${userRequest?.username} : ${userRequest?.password}" )))
        }
    }catch (e : Exception){
        context.res.setBodyText(Json.encodeToString(ErrorResponse("Erreur vien de userCheck : ${e.message}")))
    }
}

@Api(routeOverride = "checkuserid")
suspend fun checkUserId(context: ApiContext) {
    try {
        val idRequest = context.req.body?.decodeToString()?.let { Json.decodeFromString<IdRequest>(it) }

        val documentCount = MongoDBClient.usersCollection.countDocuments(Filters.eq("_id", idRequest?.id))
        val result = documentCount > 0

        context.res.setBodyText(Json.encodeToString(result))
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(false))
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
            val user = User(
                username = userRequest.username,
                password = hashPassword(userRequest.password)
            )
            val doc = user.toDocument()
            val isInserted = MongoDBClient.usersCollection.insertOne(doc).wasAcknowledged()

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




private fun hashPassword(password: String?): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashBytes = messageDigest.digest(password?.toByteArray(StandardCharsets.UTF_8))
    val hexString = StringBuffer()

    for (byte in hashBytes) {
        hexString.append(String.format("%02x", byte))
    }

    return hexString.toString()
}

@Serializable
data class IdRequest(val id: String)
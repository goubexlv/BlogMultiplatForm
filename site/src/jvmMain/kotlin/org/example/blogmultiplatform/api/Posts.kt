package org.example.blogmultiplatform.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.codecs.ObjectIdGenerator
import org.example.blogmultiplatform.models.Post
import org.example.blogmultiplatform.models.User
import org.example.blogmultiplatform.util.MongoDBClient


@Api(routeOverride = "addpost")
suspend fun addPost(context: ApiContext) {
    try {
        val requestPost = context.req.body?.decodeToString()?.let { Json.decodeFromString<Post>(it) }

        if(requestPost != null){

            val doc = requestPost.toDocument()
            val isInserted = MongoDBClient.postCollection.insertOne(doc).wasAcknowledged()

            if (isInserted) {
                context.res.setBodyText(Json.encodeToString(mapOf("message" to "Post ajouté avec succès $doc")))
            } else {
                context.res.setBodyText(Json.encodeToString(ErrorResponse("Échec de l'ajout du Post")))
            }

        }else {
            context.res.setBodyText(Json.encodeToString(ErrorResponse("Requête invalide ${requestPost?.title}")))

        }

    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(ErrorResponse("Erreur lors de l'ajout de l'utilisateur : ${e.message}")))
    }
}
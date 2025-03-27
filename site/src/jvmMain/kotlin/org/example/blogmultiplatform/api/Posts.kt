package org.example.blogmultiplatform.api

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Indexes.descending
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.models.ApiListResponse
import org.example.blogmultiplatform.models.Post
import org.example.blogmultiplatform.models.PostWithoutDetails
import org.example.blogmultiplatform.util.Constants.POSTS_PER_PAGE
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

@Api(routeOverride = "readmyposts")
suspend fun readMyPosts(context: ApiContext) {
    try {
        val skip = context.req.params["skip"]?.toInt() ?: 0
        val author = context.req.params["author"] ?: ""

        val myPosts = MongoDBClient.postCollection
            .find(Filters.eq("author", author))
            .sort(descending("date"))
            .skip(skip)
            .limit(POSTS_PER_PAGE)
            .map { PostWithoutDetails.fromDocument(it) }
            .toList()

        context.res.setBodyText(Json.encodeToString(ApiListResponse.Success(data = myPosts)))
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(ApiListResponse.Error(message = e.message.toString())))
    }
}
package org.example.blogmultiplatform.data

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters

import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.Document
import org.example.blogmultiplatform.models.User
import org.example.blogmultiplatform.util.Constants.DATABASE_NAME
import org.example.blogmultiplatform.util.MongoDBClient



class MongoDB(private val context: InitApiContext) : MongoRepository {


    override suspend fun checkUserExistence(user: User): User? {
        try {
            val filter = Filters.and(
                Filters.eq("username", user.username),
                Filters.eq("password", user.password)
            )
            val user =MongoDBClient.usersCollection
                .find(
                    filter
                ).firstOrNull()

            if (user == null) {
                return null
            }
            return User(
                id = user["_id"].toString(),
                username = user["username"]?.toString() ?: "",
                password = user["password"]?.toString() ?: "",

            )
        } catch (e : Exception) {
            context.logger.error(e.message.toString())
            return null
        }
    }



    override suspend fun insertUser(user: User): Boolean {
        return try {
            val doc = user.toDocument()
            val result = MongoDBClient.usersCollection.insertOne(doc)

            if (result.insertedId != null) {
                true
            } else {
                false
            }
        } catch (e: Exception) {
            context.logger.error("Erreur lors de l'insertion de l'utilisateur : ${e.message}")
            false // ❌ Erreur lors de l'insertion
        }
    }


}
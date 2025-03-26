package org.example.blogmultiplatform.util

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.example.blogmultiplatform.util.Constants.COLLECTION_NAME
import org.example.blogmultiplatform.util.Constants.COLLECTION_NAME_POST
import org.example.blogmultiplatform.util.Constants.USER
import org.example.blogmultiplatform.util.Constants.DATABASE_NAME
import org.example.blogmultiplatform.util.Constants.HOST
import org.example.blogmultiplatform.util.Constants.MAX_POOL_SIZE
import org.example.blogmultiplatform.util.Constants.PASSWORD
import org.example.blogmultiplatform.util.Constants.PORT

object MongoDBClient {

    private val database: MongoDatabase
    val usersCollection: MongoCollection<Document>
    val postCollection: MongoCollection<Document>

    init {
        val credentials = if (USER.isNotBlank() && PASSWORD.isNotBlank()) {
            "$USER:$PASSWORD@"
        } else {
            ""
        }

        val uri = "mongodb://$credentials$HOST:$PORT/?maxPoolSize=$MAX_POOL_SIZE&w=majority"
        val mongoClient = MongoClients.create(uri)
        database = mongoClient.getDatabase(DATABASE_NAME)
        usersCollection = database.getCollection(COLLECTION_NAME)
        postCollection = database.getCollection(COLLECTION_NAME_POST)
    }
}
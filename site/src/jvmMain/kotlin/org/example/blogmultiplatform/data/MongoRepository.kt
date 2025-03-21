package org.example.blogmultiplatform.data

import org.example.blogmultiplatform.models.User

interface MongoRepository {
    suspend fun checkUserExistence(user: User) : User?
    suspend fun insertUser(user: User): Boolean
}
package org.example.blogmultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.Document
import org.bson.types.ObjectId

@Serializable
actual data class User(
    @SerialName(value = "_id")
    actual val id: String = ObjectId().toHexString(),
    actual val username : String = "",
    actual val password : String = ""
){
    fun toDocument(): Document = Document.parse(Json.encodeToString(this))

    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun fromDocument(document: Document): User = json.decodeFromString(document.toJson())
    }
}

@Serializable
actual data class UserWithoutPassword(
    @SerialName(value = "_id")
    actual val id: String = ObjectId().toHexString().toString(),
    actual val username: String = ""
)
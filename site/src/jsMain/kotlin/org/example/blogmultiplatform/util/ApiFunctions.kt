package org.example.blogmultiplatform.util

import com.varabyte.kobweb.browser.api
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.models.User
import org.example.blogmultiplatform.models.UserWithoutPassword
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.js.Date

suspend fun checkUserExistence(user : User) : UserWithoutPassword? {
    return try {
        val requestBody = Json.encodeToString(user).encodeToByteArray()

        // Envoie la requête POST à l'API
        val response = window.api.tryPost(
            apiPath = "usercheck",
            body = requestBody
        )

        // Vérifie si la réponse est non nulle et la désérialise
        response?.decodeToString()?.let { jsonResponse ->
            Json.decodeFromString<UserWithoutPassword>(jsonResponse)
        }
    } catch (e: Exception) {
        println("Échec de la vérification de l'utilisateur : ${e.message}")
        null
    }
}

inline fun <reified T> String?.parseData(): T {
    return Json.decodeFromString(this.toString())
}

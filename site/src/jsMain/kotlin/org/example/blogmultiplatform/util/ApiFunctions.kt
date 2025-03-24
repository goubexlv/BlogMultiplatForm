package org.example.blogmultiplatform.util

import com.varabyte.kobweb.browser.api
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.browser.window
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.models.User
import org.example.blogmultiplatform.models.UserWithoutPassword
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.js.Date

@Serializable
data class IdRequest(val id: String)

suspend fun checkUserExistence(user : User) : UserWithoutPassword? {
    return try {
        val response: HttpResponse = ApiClient.client.post("http://localhost:8080/api/usercheck") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(user)
        }

        // Affiche la réponse brute
        val responseText = response.bodyAsText()

        if (response.status == HttpStatusCode.OK) {
            return Json.decodeFromString<UserWithoutPassword>(responseText)
        } else {
            println("Erreur HTTP : ${response.status}")
            null
        }
    } catch (e: Exception) {
        println("CURRENT_USER")
        println(e.message)
        null
    }
}

suspend fun checkUserId(id: String): Boolean {
    return try {
        val response: HttpResponse = ApiClient.client.post("http://localhost:8080/api/checkuserid") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(Json.encodeToString(IdRequest(id)))
        }

        val responseText = response.bodyAsText()


        if (response.status == HttpStatusCode.OK) {
            Json.decodeFromString<Boolean>(responseText)
        } else {
            println("Erreur HTTP : ${response.status}")
            false
        }
    } catch (e: Exception) {
        println("Erreur lors de la requête : ${e.message}")
        false
    }
}



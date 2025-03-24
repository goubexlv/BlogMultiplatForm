package org.example.blogmultiplatform.util

import io.ktor.client.*
import io.ktor.client.engine.js.*  // Utilisation du moteur JS
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json


object ApiClient {
    val client = HttpClient(Js) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
}


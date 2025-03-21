package org.example.blogmultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


expect class User {
    val id: String
    val username: String
    val password: String
}


expect class UserWithoutPassword {
    val id: String
    val username: String
}
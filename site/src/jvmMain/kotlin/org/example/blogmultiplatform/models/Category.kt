package org.example.blogmultiplatform.models

import kotlinx.serialization.Serializable

@Serializable
actual enum class Category (val color: String) {
    Technology(color = Themes.Green.hex),
    Programming(color = Themes.Yellow.hex),
    Design(color = Themes.Purple.hex)
}
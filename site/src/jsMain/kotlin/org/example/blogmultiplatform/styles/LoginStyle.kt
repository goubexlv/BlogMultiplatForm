package org.example.blogmultiplatform.styles


import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.styleModifier
import org.example.blogmultiplatform.models.Themes
import org.jetbrains.compose.web.css.*

@Composable
fun loginInputModifier(isFocused: Boolean) = Modifier
    .border(2.px, LineStyle.Solid, if (isFocused) Themes.Primary.rgb else Color.transparent)
    .styleModifier {
        property("transition", "border 0.3s ease-in-out")
    }
    //.transition(CSSTransition("border", 300.ms))

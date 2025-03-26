package org.example.blogmultiplatform.styles

import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.example.blogmultiplatform.models.Themes
import org.jetbrains.compose.web.css.ms

val EditorKeyStyle = CssStyle {
    base {
        Modifier
            .backgroundColor(Colors.Transparent)
            .transition(Transition.of(property = "background", duration = 300.ms))
    }
    hover {
        Modifier.backgroundColor(Themes.Primary.rgb)
    }
}
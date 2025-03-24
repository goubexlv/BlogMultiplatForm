package org.example.blogmultiplatform.styles

import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import org.example.blogmultiplatform.models.Themes
import org.example.blogmultiplatform.util.Id
import org.jetbrains.compose.web.css.ms

val NavigationItemStyle = CssStyle {

    cssRule(" > #${Id.svgParent} > #${Id.vectorIcon}") {
        Modifier
            .transition(Transition.of(property = TransitionProperty.All, duration = 300.ms))
            .styleModifier {
                property("stroke", Themes.White.hex)
            }
    }

    cssRule(":hover > #${Id.svgParent} > #${Id.vectorIcon}") {
        Modifier
            .styleModifier {
                property("stroke", Themes.Primary.hex)
            }
    }

    cssRule(" > #${Id.navigationText}") {
        Modifier
            .transition(Transition.of(property = TransitionProperty.All, duration = 300.ms))
            .color(Themes.White.rgb)
    }

    cssRule(":hover > #${Id.navigationText}") {
        Modifier
            //.transition(Transition.of(property = TransitionProperty.All, duration = 300.ms))
            .color(Themes.Primary.rgb)
    }
}
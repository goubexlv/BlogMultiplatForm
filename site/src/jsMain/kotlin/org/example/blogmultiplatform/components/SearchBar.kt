package org.example.blogmultiplatform.components

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.icons.fa.FaMagnifyingGlass
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import org.example.blogmultiplatform.models.Themes
import org.example.blogmultiplatform.util.noBorder
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Input

@Composable
fun SearchBar(onEnterClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(left = 20.px)
            .height(54.px)
            .backgroundColor(Themes.LightGray.rgb)
            .borderRadius(r = 100.px)
            .border(
                width = 2.px,
                style = LineStyle.Solid,
                color = if(focused) Themes.Primary.rgb else Themes.LightGray.rgb
            )
            .transition(Transition.of(property = "border", duration = 200.ms)),
        verticalAlignment = Alignment.CenterVertically
    ){
        FaMagnifyingGlass(
            modifier = Modifier
                .color(if (focused) Themes.Primary.rgb else Themes.DarkGray.rgb)
                .margin(right = 14.px),
            size = IconSize.SM
        )
        Input(
            type = InputType.Text,
            attrs = Modifier
                .fillMaxWidth()
                .color(Colors.Black)
                .backgroundColor(Colors.Transparent)
                .noBorder()
                .onFocusIn { focused = true }
                .onFocusOut { focused = false }
                .onKeyDown {
                    if(it.key == "Enter") {
                        onEnterClick()
                    }
                }
                .toAttrs{
                    attr("placeholder", "Search...")
                }

        )

    }


}
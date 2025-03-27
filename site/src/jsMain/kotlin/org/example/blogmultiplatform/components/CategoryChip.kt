package org.example.blogmultiplatform.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.blogmultiplatform.models.Category
import org.example.blogmultiplatform.models.Themes
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.example.blogmultiplatform.util.constants.FONT_FAMILY


@Composable
fun CategoryChip(category: Category){
    Box (
        modifier = Modifier
            .height(32.px)
            .padding(leftRight = 14.px)
            .borderRadius(r = 100.px)
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color = Themes.HalfBlack.rgb
            ),
        contentAlignment = Alignment.Center
    ){
        SpanText(
            modifier = Modifier
                .fontFamily(FONT_FAMILY)
                .fontSize(12.px)
                .color(Themes.HalfBlack.rgb),
            text = category.name
        )

    }
}
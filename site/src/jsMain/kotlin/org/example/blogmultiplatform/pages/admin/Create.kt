package org.example.blogmultiplatform.pages.admin

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.example.blogmultiplatform.components.AdminPageLayout
import org.example.blogmultiplatform.components.SidePanel
import org.example.blogmultiplatform.models.Category
import org.example.blogmultiplatform.models.Themes
import org.example.blogmultiplatform.util.constants.FONT_FAMILY
import org.example.blogmultiplatform.util.constants.PAGE_WIDTH
import org.example.blogmultiplatform.util.constants.SIDE_PANEL_WIDTH
import org.example.blogmultiplatform.util.isUserLoggedIn
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul

@Page
@Composable
fun CreatePage() {
    isUserLoggedIn {
        CreateScreen()
    }
}

@Composable
fun CreateScreen() {
    val breakpoint = rememberBreakpoint()
    var popularSwitch by remember { mutableStateOf(false) }
    var mainrSwitch by remember { mutableStateOf(false) }
    var sponsoredSwitch by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(Category.Programming) }
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }

    AdminPageLayout {
        Box (
            modifier = Modifier
                .fillMaxSize()
                .margin(topBottom = 50.px)
                .padding(left = if(breakpoint > Breakpoint.MD) SIDE_PANEL_WIDTH.px else 0.px),
            contentAlignment = Alignment.TopCenter
        ){
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .maxWidth(700.px),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                SimpleGrid(numColumns = numColumns(base = 1, sm = 3)){
                    Row (
                        modifier = Modifier
                            .margin(
                                right = if(breakpoint < Breakpoint.SM) 0.px else 24.px,
                                bottom = if(breakpoint < Breakpoint.SM) 12.px else 0.px
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Switch(
                            modifier = Modifier.margin(right = 8.px),
                            checked = popularSwitch,
                            onCheckedChange = {popularSwitch = it},
                            size = SwitchSize.LG
                        )
                        SpanText(
                            modifier = Modifier
                                .fontSize(14.px)
                                .fontFamily(FONT_FAMILY)
                                .color(Themes.HalfBlack.rgb),
                            text = "Popular"
                        )

                    }

                    Row (
                        modifier = Modifier
                            .margin(
                                right = if(breakpoint < Breakpoint.SM) 0.px else 24.px,
                                bottom = if(breakpoint < Breakpoint.SM) 12.px else 0.px
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Switch(
                            modifier = Modifier.margin(right = 8.px),
                            checked = mainrSwitch,
                            onCheckedChange = {mainrSwitch = it},
                            size = SwitchSize.LG
                        )
                        SpanText(
                            modifier = Modifier
                                .fontSize(14.px)
                                .fontFamily(FONT_FAMILY)
                                .color(Themes.HalfBlack.rgb),
                            text = "Main"
                        )

                    }

                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Switch(
                            modifier = Modifier.margin(right = 8.px),
                            checked = sponsoredSwitch,
                            onCheckedChange = {sponsoredSwitch = it},
                            size = SwitchSize.LG
                        )
                        SpanText(
                            modifier = Modifier
                                .fontSize(14.px)
                                .fontFamily(FONT_FAMILY)
                                .color(Themes.HalfBlack.rgb),
                            text = "Sponsored"
                        )

                    }
                }
                Input(
                    type = InputType.Text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(topBottom = 12.px)
                        .padding(leftRight = 20.px)
                        .backgroundColor(Themes.LightGray.rgb)
                        .borderRadius(r = 4.px)
                        .border(
                            width = 0.px,
                            style = LineStyle.None,
                            color = Color.transparent
                        )
                        .outline(
                            width = 0.px,
                            style = LineStyle.None,
                            color = Color.transparent
                        )
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px),
                    placeholder = "Title",
                    value = title,
                    onValueChange = { title = it }

                )

                Input(
                    type = InputType.Text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .backgroundColor(Themes.LightGray.rgb)
                        .borderRadius(r = 4.px)
                        .border(
                            width = 0.px,
                            style = LineStyle.None,
                            color = Color.transparent
                        )
                        .outline(
                            width = 0.px,
                            style = LineStyle.None,
                            color = Color.transparent
                        )
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px),
                    placeholder = "Subtitle",
                    value = subtitle,
                    onValueChange = { subtitle = it }

                )
                categoryDropdown(
                    selectedCategory = selectedCategory,
                    onCategorySelect = {selectedCategory = it}
                )

            }

        }

    }
}


@Composable
fun categoryDropdown(
    selectedCategory: Category,
    onCategorySelect: (Category) -> Unit
) {
    Box (
        modifier = Modifier
            .margin(topBottom = 12.px)
            .classNames("dropdown")
            .fillMaxWidth()
            .height(54.px)
            .backgroundColor(Themes.LightGray.rgb)
            .cursor(Cursor.Pointer)
            .attrsModifier {
                attr("data-bs-toggle", "dropdown")
            }
    ){
        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(leftRight = 20.px),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            SpanText(
                modifier = Modifier
                    .fillMaxWidth()
                    .fontSize(16.px)
                    .fontFamily(FONT_FAMILY),
                text = selectedCategory.name
            )
            Box(modifier = Modifier.classNames("dropdown-toggle"))
        }
        Ul(
            attrs = Modifier
                .fillMaxWidth()
                .classNames("dropdown-menu")
                .toAttrs()
        ) {
            Category.entries.forEach { category ->
                Li {
                    A(
                        attrs = Modifier
                            .classNames("dropdown-item")
                            .color(Colors.Black)
                            .fontFamily(FONT_FAMILY)
                            .fontSize(16.px)
                            .onClick { onCategorySelect(category) }
                            .toAttrs()
                    ) {
                        Text(value = category.name)
                    }
                }
            }
        }
    }

}

























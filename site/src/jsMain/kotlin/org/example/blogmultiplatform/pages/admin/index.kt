package org.example.blogmultiplatform.pages.admin

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaPlus
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.browser.localStorage
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.components.AdminPageLayout
import org.example.blogmultiplatform.components.LoadingIndicator
import org.example.blogmultiplatform.models.RandomJoke
import org.example.blogmultiplatform.models.Themes
import org.example.blogmultiplatform.navigation.Screen
import org.example.blogmultiplatform.util.ApiClient
import org.example.blogmultiplatform.util.Res
import org.example.blogmultiplatform.util.constants.FONT_FAMILY
import org.example.blogmultiplatform.util.constants.HUMOR_API_URL
import org.example.blogmultiplatform.util.constants.PAGE_WIDTH
import org.example.blogmultiplatform.util.constants.SIDE_PANEL_WIDTH
import org.example.blogmultiplatform.util.fetchRandomJoke
import org.example.blogmultiplatform.util.isUserLoggedIn
import org.jetbrains.compose.web.css.*
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.js.Date

@Page
@Composable
fun HomePage(){
    isUserLoggedIn {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    val scope = rememberCoroutineScope()
    var randomJoke: RandomJoke? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit){
        fetchRandomJoke { randomJoke = it }
    }

    AdminPageLayout {
        HomeContent(randomJoke = randomJoke)
        AddButton()

    }
}

@Composable
fun HomeContent(randomJoke : RandomJoke?){
    val breakpoint = rememberBreakpoint()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(left = if (breakpoint > Breakpoint.MD) SIDE_PANEL_WIDTH.px else 0.px),
        contentAlignment = Alignment.Center
    ) {
        if(randomJoke != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(topBottom = 50.px),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(randomJoke.id != -1) {
                    Image(
                        modifier = Modifier
                            .size(150.px)
                            .margin(bottom = 50.px),
                        src = Res.Image.laugh,
                        description = "Laugh Image"
                    )
                }
                if (randomJoke.joke.contains("Q:")){
                    SpanText(
                        modifier = Modifier
                            .margin(bottom = 14.px)
                            .fillMaxWidth(40.percent)
                            .textAlign(TextAlign.Center)
                            .color(Themes.Secondary.rgb)
                            .fontFamily(FONT_FAMILY)
                            .fontSize(28.px)
                            .fontWeight(FontWeight.Bold),
                        text = randomJoke.joke.split(":")[1].dropLast(1)
                    )
                    SpanText(
                        modifier = Modifier
                            .fillMaxWidth(60.percent)
                            .textAlign(TextAlign.Center)
                            .color(Themes.HalfBlack.rgb)
                            .fontSize(20.px)
                            .fontFamily(FONT_FAMILY)
                            .fontWeight(FontWeight.Normal),
                        text = randomJoke.joke.split(":").last()
                    )
                }else {
                    SpanText(
                        modifier = Modifier
                            .margin(bottom = 14.px)
                            .fillMaxWidth(40.percent)
                            .textAlign(TextAlign.Center)
                            .color(Themes.Secondary.rgb)
                            .fontFamily(FONT_FAMILY)
                            .fontSize(28.px)
                            .fontWeight(FontWeight.Bold),
                        text = randomJoke.joke
                    )

                }

            }
        } else {
            LoadingIndicator()
        }
    }
}

@Composable
fun AddButton() {
    val breakpoint = rememberBreakpoint()
    val context = rememberPageContext()
    Box (
        modifier = Modifier
            .height(100.vh)
            .fillMaxWidth()
            .maxWidth(PAGE_WIDTH.px)
            .position(Position.Fixed)
            .styleModifier {
                property("pointer-events", "none")
            },
        contentAlignment = Alignment.BottomEnd
    ) {
        Box (
            modifier = Modifier
                .margin(
                    right = if(breakpoint > Breakpoint.MD) 40.px else 20.px,
                    bottom = if(breakpoint > Breakpoint.MD) 40.px else 20.px
                )
                .backgroundColor(Themes.Primary.rgb)
                .size(if(breakpoint > Breakpoint.MD) 80.px else 50.px)
                .borderRadius(r=14.px)
                .cursor(Cursor.Pointer)
                .onClick {
                    context.router.navigateTo(Screen.AdminCreate.route)
                }
                .styleModifier {
                    property("pointer-events", "auto")
                },
            contentAlignment = Alignment.Center
        ){
            FaPlus(
                modifier = Modifier.color(Color.white),
                size = IconSize.LG
            )
        }

    }
}







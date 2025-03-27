package org.example.blogmultiplatform.pages.admin

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.example.blogmultiplatform.components.AdminPageLayout
import org.example.blogmultiplatform.components.SearchBar
import org.example.blogmultiplatform.components.SidePanel
import org.example.blogmultiplatform.util.constants.PAGE_WIDTH
import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.blogmultiplatform.components.Posts
import org.example.blogmultiplatform.models.ApiListResponse
import org.example.blogmultiplatform.models.Post
import org.example.blogmultiplatform.models.PostWithoutDetails
import org.example.blogmultiplatform.models.Themes
import org.example.blogmultiplatform.util.constants.FONT_FAMILY
import org.example.blogmultiplatform.util.constants.SIDE_PANEL_WIDTH
import org.example.blogmultiplatform.util.fetchMyPosts
import org.example.blogmultiplatform.util.isUserLoggedIn
import org.example.blogmultiplatform.util.noBorder
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button

@Page("/admin/myposts")
@Composable
fun MyPostsPage() {
    isUserLoggedIn {
        MyPostsScreen()
    }
}

@Composable
fun MyPostsScreen() {
    val breakpoint = rememberBreakpoint()
    var selectable by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("Select") }
    val myPosts = remember { mutableStateListOf<PostWithoutDetails>() }

    LaunchedEffect(Unit) {
        fetchMyPosts(
            skip = 0,
            onSuccess = {
                if(it is ApiListResponse.Success){
                    myPosts.addAll(it.data)
                }
            },
            onError = {
                println(it)
            }
        )
    }

    AdminPageLayout {
        Column (
            modifier = Modifier
                .margin(topBottom = 50.px)
                .fillMaxSize()
                .padding(left = if(breakpoint > Breakpoint.MD) SIDE_PANEL_WIDTH.px else 0.px),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box (
                modifier = Modifier
                    .fillMaxWidth(
                        if (breakpoint > Breakpoint.MD) 30.percent
                        else 50.percent
                    )
                    .margin(bottom = 24.px),
                contentAlignment = Alignment.Center
            ){
                SearchBar(
                    onEnterClick = {}
                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth(
                        if (breakpoint > Breakpoint.MD) 80.percent
                        else 90.percent
                    )
                    .margin(bottom = 24.px),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Row (verticalAlignment = Alignment.CenterVertically)
                {
                    Switch(
                        modifier = Modifier.margin(right = 8.px),
                        size = SwitchSize.LG,
                        checked = selectable,
                        onCheckedChange = {
                            selectable = it

                        }
                    )
                    SpanText(
                        modifier = Modifier.color(if(selectable) Colors.Black else Themes.HalfBlack.rgb),
                        text = text
                    )
                }
                Button (
                    attrs = Modifier
                        .margin(right = 20.px)
                        .height(54.px)
                        .padding(leftRight = 24.px)
                        .backgroundColor(Themes.Red.rgb)
                        .color(Colors.White)
                        .noBorder()
                        .borderRadius(r = 4.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(14.px)
                        .fontWeight(FontWeight.Medium)
                        .onClick {

                        }
                        .toAttrs()
                ){
                    SpanText(text = "Delete")
                }

            }
            Posts(
                breakpoint = breakpoint,
                posts = myPosts
            )

        }
    }
}
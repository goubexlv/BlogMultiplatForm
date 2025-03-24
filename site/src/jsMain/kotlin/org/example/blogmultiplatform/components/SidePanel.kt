package org.example.blogmultiplatform.components

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.rememberPageContext
import org.example.blogmultiplatform.models.Themes
import org.example.blogmultiplatform.util.constants.SIDE_PANEL_WIDTH
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaBars
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.example.blogmultiplatform.navigation.Screen
import org.example.blogmultiplatform.styles.NavigationItemStyle
import org.example.blogmultiplatform.util.Id
import org.example.blogmultiplatform.util.Res
import org.example.blogmultiplatform.util.constants.COLLAPSED_PANEL_HEIGHT
import org.example.blogmultiplatform.util.constants.FONT_FAMILY
import org.example.blogmultiplatform.util.logout
import org.jetbrains.compose.web.css.percent


@Composable
fun SidePanel(onMenuClick: () -> Unit){
    val breakpoint = rememberBreakpoint()
    if (breakpoint > Breakpoint.MD) {
        SidePanelInternal()
    }else{
        CollapsedSidePanel(onMenuClick = onMenuClick)
    }
}



@Composable
private fun SidePanelInternal() {

    Column (
        modifier = Modifier
            .padding(leftRight = 40.px, topBottom = 50.px)
            .width(SIDE_PANEL_WIDTH.px)
            .height(100.vh)
            .position(Position.Fixed)
            .backgroundColor(Themes.Secondary.rgb)
            .zIndex(9)
    ) {
        Image(
            modifier = Modifier.margin(bottom = 60.px),
            src = Res.Image.logo,
            description = "Logo Image"
        )
        NavigationItems()

    }

}

@Composable
private fun NavigationItems(){
    val context = rememberPageContext()
    SpanText(
        modifier = Modifier
            .margin(bottom = 30.px)
            .fontFamily(FONT_FAMILY)
            .fontSize(14.px)
            .color(Themes.Halfwhite.rgb),
        text = "Dashboard"
    )
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        title = "Home",
        selected = context.route.path == Screen.AdminHome.route,
        icon = Res.PathIcon.home,
        onClick = {
            context.router.navigateTo(Screen.AdminHome.route)
        }
    )

    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        selected = context.route.path == Screen.AdminCreate.route,
        title = "Create Post",
        icon = Res.PathIcon.create,
        onClick = {
            context.router.navigateTo(Screen.AdminCreate.route)
        }
    )

    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        selected = context.route.path == Screen.AdminMyPosts.route,
        title = "My Post",
        icon = Res.PathIcon.posts,
        onClick = {
            context.router.navigateTo(Screen.AdminMyPosts.route)
        }
    )

    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        title = "Logout",
        icon = Res.PathIcon.logout,
        onClick = {
            logout()
            context.router.navigateTo(Screen.AdminLogin.route)
        }
    )
}


@Composable
private fun NavigationItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    title : String,
    icon : String,
    onClick: () -> Unit
){
    Row (
        modifier = NavigationItemStyle.toModifier()
            .then(modifier)
            .cursor(Cursor.Pointer)
            .onClick { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        VectorIcon(
            modifier = Modifier.margin(right = 10.px),
            selected = selected,
            pathData = icon
        )

        SpanText(
            modifier = Modifier
                .id(Id.navigationText)
                .fontFamily(FONT_FAMILY)
                .fontSize(16.px)
                .thenIf(
                    condition = selected,
                    other = Modifier.color(Themes.Primary.rgb)
                ),
            text = title
        )
    }

}

@Composable
private fun VectorIcon(
    modifier: Modifier = Modifier,
    selected: Boolean,
    pathData : String
){
    Svg(
        attrs = modifier
            .id(Id.svgParent)
            .width(24.px)
            .height(24.px)
            .toAttrs {
                attr("viewBox", "0 0 24 24")
                attr("fill", "none")
            }
    ) {
        Path (
            attrs = modifier
                .id(Id.vectorIcon)
                .thenIf(
                    condition = selected,
                    other = Modifier.styleModifier {
                        property("stroke", Themes.Primary.hex)
                    }
                )
                .toAttrs{
                    attr(attr = "d", value = pathData)
                    attr(attr = "stroke-width", value = "2")
                    attr(attr = "stroke-linecap", value = "round")
                    attr(attr = "stroke-linejoin", value = "round")
                }
        )

    }
}

@Composable
private fun CollapsedSidePanel(onMenuClick: () -> Unit){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(COLLAPSED_PANEL_HEIGHT.px)
            .padding(leftRight = 24.px)
            .backgroundColor(Themes.Secondary.rgb),
        verticalAlignment = Alignment.CenterVertically
        ){
        FaBars(
            modifier = Modifier
                .margin(right = 24.px)
                .color(Colors.White)
                .cursor(Cursor.Pointer)
                .onClick { onMenuClick() },
            size = IconSize.XL
        )

        Image(
            modifier = Modifier.width(80.px),
            src = Res.Image.logo,
            description = "Logo Image"
        )

    }
}

@Composable
fun OverflowSidePanel(
    onMenuClose: () -> Unit
){
    val breakpoint = rememberBreakpoint()

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(9)
            .backgroundColor(Themes.HalfBlack.rgb)
    ){
        Column (
            modifier = Modifier
                .padding(all = 24.px)
                .fillMaxHeight()
                .width(if(breakpoint < Breakpoint.MD) 50.percent else 25.percent )
                .backgroundColor(Themes.Secondary.rgb)
        ){
            Row (
                modifier = Modifier.margin(bottom = 24.px),
                verticalAlignment = Alignment.CenterVertically
            ){
                FaXmark(
                    modifier = Modifier
                        .margin(right = 60.px)
                        .color(Colors.White)
                        .cursor(Cursor.Pointer)
                        .onClick {
                            onMenuClose()
                        },
                    size = IconSize.LG
                )
                Image(
                    modifier = Modifier.width(80.px),
                    src = Res.Image.logo,
                    description = "Logo Image"
                )
            }
            NavigationItems()

        }

    }

}













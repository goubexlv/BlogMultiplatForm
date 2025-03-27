package org.example.blogmultiplatform.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.example.blogmultiplatform.models.Post
import org.example.blogmultiplatform.models.PostWithoutDetails
import org.example.blogmultiplatform.models.Themes
import org.example.blogmultiplatform.util.constants.FONT_FAMILY
import org.example.blogmultiplatform.util.parseDateString
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun PostPreview(post : PostWithoutDetails){
    Column (
        modifier = Modifier
            .fillMaxWidth(95.percent)
            .margin(bottom = 24.px)
            .cursor(Cursor.Pointer)
    ){
        Image(
            modifier = Modifier
                .padding(bottom = 16.px)
                .fillMaxWidth()
                .objectFit(ObjectFit.Cover),
            src = post.thumbnail,
            description = "Post Thumbnail Image"
        )
        SpanText(
            modifier = Modifier
                .fontFamily(FONT_FAMILY)
                .fontSize(12.px)
                .color(Themes.HalfBlack.rgb),
            text = post.date.parseDateString()
        )
        SpanText(
            modifier = Modifier
                .padding(bottom = 12.px)
                .fontFamily(FONT_FAMILY)
                .fontSize(20.px)
                .fontWeight(FontWeight.Bold)
                .color(Colors.Black)
                .textOverflow(TextOverflow.Ellipsis)
                .overflow(Overflow.Hidden)
                .styleModifier {
                    property("display", "-webkit-box")
                    property("-webkit-line-clamp", "2")
                    property("line-clamp", "2")
                    property("-webkit-box-orient", "vertical")
                },
            text = post.title
        )
        SpanText(
            modifier = Modifier
                .margin(bottom = 8.px)
                .fontFamily(FONT_FAMILY)
                .fontSize(20.px)
                .color(Colors.Black)
                .textOverflow(TextOverflow.Ellipsis)
                .overflow(Overflow.Hidden)
                .styleModifier {
                    property("display", "-webkit-box")
                    property("-webkit-line-clamp", "3")
                    property("line-clamp", "3")
                    property("-webkit-box-orient", "vertical")
                },
            text = post.subtitle
        )
        CategoryChip(category = post.category)
    }

}

@Composable
fun Posts(
    breakpoint: Breakpoint,
    posts: List<PostWithoutDetails>
){
    Column (
        modifier = Modifier.fillMaxWidth(if (breakpoint > Breakpoint.MD) 80.percent else 90.percent
        ),
        verticalArrangement = Arrangement.Center
    ){
        SimpleGrid(
            modifier = Modifier.fillMaxWidth(),
            numColumns = numColumns(base= 1,sm=2,md=3,lg=4)
        ){
            posts.forEach{
                PostPreview(post = it)
            }

        }
    }
}




















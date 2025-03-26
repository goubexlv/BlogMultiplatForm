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
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.browser.localStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.blogmultiplatform.models.Themes
import org.example.blogmultiplatform.models.User
import org.example.blogmultiplatform.models.UserWithoutPassword
import org.example.blogmultiplatform.navigation.Screen
import org.example.blogmultiplatform.util.Res
import org.example.blogmultiplatform.util.checkUserExistence
import org.example.blogmultiplatform.util.constants.FONT_FAMILY
import org.example.blogmultiplatform.util.noBorder
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.w3c.dom.set


@Page
@Composable
fun LoginScreen(){
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    var errorText by remember { mutableStateOf(" ") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(leftRight = 50.px, top = 80.px, bottom = 24.px)
                .backgroundColor(Themes.LightGray.rgb),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .margin(bottom = 50.px)
                    .width(100.px),
                src = Res.Image.logo,
                description = "logo image"
            )

            Input(
                type = InputType.Text,
                modifier = Modifier
                    .margin(bottom = 12.px)
                    .width(350.px)
                    .height(54.px)
                    .backgroundColor(Color.white)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .noBorder()
                    .padding(leftRight = 20.px),
                placeholder = "Username",
                value = username,
                onValueChange = { username = it },

            )

            Input(
                type = InputType.Password,
                modifier = Modifier
                    .margin(bottom = 20.px)
                    .width(350.px)
                    .height(54.px)
                    .backgroundColor(Color.white)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .noBorder()
                    .padding(leftRight = 20.px),
                placeholder = "Password",
                value = password,
                onValueChange = { password = it }
                )

            Button(
                attrs = Modifier
                    .margin(bottom = 24.px )
                    .width(350.px)
                    .height(54.px)
                    .backgroundColor(Themes.Primary.rgb)
                    .color(Color.white)
                    .borderRadius(r=4.px)
                    .fontFamily(FONT_FAMILY)
                    .fontWeight(FontWeight.Medium)
                    .fontSize(14.px)
                    .noBorder()
                    .cursor(Cursor.Pointer)
                    .onClick {
                        scope.launch {
                            if(username.isNotEmpty() && password.isNotEmpty()) {
                                val user = checkUserExistence(
                                    user = User(
                                        username = username,
                                        password = password
                                    )
                                )
                                if(user != null){
                                    rememberLoggedIn(remember = true, user = user)
                                    context.router.navigateTo(Screen.AdminHome.route)
                                } else {
                                    errorText = "L'utilisateur n'existe pas.${user?.username}"
                                    delay(3000)
                                    errorText = " "
                                }
                            } else {
                                errorText = "Vous devez remplir les champs vides."
                                delay(3000)
                                errorText = " "
                            }
                        }

                    }
                    .toAttrs()
            ){
                SpanText(text = "Login")
            }

            SpanText(
                modifier = Modifier
                    .width(350.px)
                    .color(Color.red)
                    .fontFamily(FONT_FAMILY)
                    .textAlign(TextAlign.Center),
                text = errorText
            )

        }

    }
}

private fun rememberLoggedIn(
    remember: Boolean,
    user: UserWithoutPassword? = null
) {
    localStorage["remember"] = remember.toString()
    if (user != null) {
        localStorage["userId"] = user.id
        localStorage["username"] = user.username
    }
}


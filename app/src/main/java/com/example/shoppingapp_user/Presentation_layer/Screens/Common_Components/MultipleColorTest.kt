package com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.notificationColor

@Composable
fun MultiColorText(
    firstText:String ,
    secondText:String ,
    onClick : ()->Unit
)
{
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = notificationColor,)) {
            append(firstText)
        }

        withStyle(style = SpanStyle(color = darkPink)) {
            append(secondText)
        }
    }
    Text(text = annotatedString,modifier=Modifier.clickable (interactionSource = remember { MutableInteractionSource() },
        indication = null){
        onClick()
    })
}

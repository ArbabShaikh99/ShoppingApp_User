package com.example.shoppingapp_user.Presentation_layer.Screens.Shiping.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray



@Composable
fun ShipingTextField(
    value :String,
    valueChange: (String) -> Unit,
    Placeholder :String,
    widthDefine : Dp
){


    BasicTextField(
        value = value,
        onValueChange = { valueChange(it) },
        textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
        cursorBrush = SolidColor(darkPink),
        modifier = Modifier
            .width(widthDefine)
            .height(36.dp)
            .background(lightGray, shape = RoundedCornerShape(20))
            .border(1.dp, darkPink, RoundedCornerShape(20))
            .padding(horizontal = 8.dp, vertical = 2.dp),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart // ✅ Cursor aur placeholder ko align karega
            ) {
                if (value.isEmpty()) {
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Gray, fontSize = 13.sp)) {
                                append(Placeholder)
                            }
                        }
                    )
                }
                innerTextField()
            }
        }
    )
}

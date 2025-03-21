package com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    value :String,
    onValueChange :(String)-> Unit,
    placeHolder : String,
    widthSize : Dp,
    heightSize : Dp,
    TextFontSize : TextUnit ,

    ){


    TextField(
        value = value,
        onValueChange = { onValueChange(it)},
        placeholder = { Text(placeHolder, color = customGray, fontSize = TextFontSize) },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null, tint = customGray,
                modifier = Modifier
                    .size(28.dp)
            )
        },
        colors = TextFieldDefaults.colors(
            cursorColor = darkPink ,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = lightGray,
            unfocusedContainerColor = lightGray
        ),

                modifier = Modifier
            .width(widthSize)
            .height(heightSize)
            .border(
                width = 1.dp,
                color = darkPink,
                shape = RoundedCornerShape(15.dp)
            )
    )
}




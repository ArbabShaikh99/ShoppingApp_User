package com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray
import com.example.shoppingapp_user.ui.theme.notificationColor
import com.example.shoppingapp_user.ui.theme.pink
import androidx.compose.foundation.background


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    value: String,
    valueChange: (String) -> Unit,
    placeHolderValue: String,
    readOnly : Boolean = false,
    keyboardType : KeyboardType = KeyboardType.Text,
) {
    TextField(
        value = value,
        readOnly = readOnly,
        onValueChange = { valueChange(it) },
        textStyle = TextStyle(
            color = notificationColor,
            fontSize = 17.sp
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,  // Numeric input
            imeAction = ImeAction.Done
        ),

//        colors = TextFieldDefaults.textFieldColors(
//            containerColor = lightGray,
//            focusedIndicatorColor = Color.Transparent,
//            cursorColor = darkPink,
//            unfocusedIndicatorColor = Color.Transparent
//        ),
        colors = TextFieldDefaults.colors(
            cursorColor = darkPink ,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = lightGray,
            unfocusedContainerColor = lightGray
        ),
        modifier = Modifier
            .width(334.dp)
            .height(52.dp)
            .border(
                width = 1.dp,
                color = pink,
                shape = RoundedCornerShape(15.dp)
            ),
        maxLines = 1,
        singleLine = true,
        label = {
            Text(placeHolderValue, color = customGray, fontSize = 13.sp) // ✅ Placeholder will stay even when typing
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaceBook_GoogleTextField(
    placHolderValue: String,
    icon: Int,
    onClick: () -> Unit
) {
    TextField(
        value = "",
        onValueChange = {},
        readOnly = true,
        placeholder = { Text(placHolderValue, color = customGray) },
        textStyle = TextStyle(color = notificationColor),
        colors = TextFieldDefaults.colors(
            cursorColor = darkPink ,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = lightGray,
            unfocusedContainerColor = lightGray
        ),
        modifier = Modifier
            .width(334.dp)
            .height(54.dp)
            .border(
                width = 1.dp,
                color = pink,
                shape = RoundedCornerShape(15.dp)
            )
            .clickable { onClick() },
        maxLines = 1,

        leadingIcon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = Color.Unspecified
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField2(
    value: String,
    valueChange: (String) -> Unit,
    placeHolderValue: String,
    readOnly : Boolean = false,
) {
    TextField(
        value = value,
        readOnly = readOnly,
        onValueChange = { valueChange(it) },
        textStyle = TextStyle(
            color = notificationColor,
            fontSize = 17.sp
        ),
        colors = TextFieldDefaults.colors(
            cursorColor = darkPink ,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = lightGray,
            unfocusedContainerColor = lightGray
        ),
        modifier = Modifier
            .width(154.dp)
            .height(52.dp)
            .border(
                width = 1.dp,
                color = pink,
                shape = RoundedCornerShape(15.dp)
            ),
        maxLines = 1,
        singleLine = true,
        label = {
            Text(placeHolderValue, color = customGray, fontSize =13.sp ) // ✅ Placeholder will stay even when typing
        }
    )
}


@Composable
fun ImageFieldCard(
    placeholderValue: String,
    icon: Int,
    imageUri: Uri?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(334.dp)
            .height(if (imageUri != null) 120.dp else 54.dp) // Adjust height if image is selected
            .border(1.dp, darkPink, RoundedCornerShape(15.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = lightGray),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Select Image Icon + Text
            Row(verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = placeholderValue,
                    color = customGray,
                    style = TextStyle(fontSize = 16.sp)
                )
            }

            // Show Selected Image if Available
            if (imageUri != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .height(100.dp) // Increased height
                        .width(550.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }
    }
}


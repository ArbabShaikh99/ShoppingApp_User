package com.example.shoppingapp_user.Presentation_layer.Screens.Auth.Component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.BlackColor
import com.example.shoppingapp_user.ui.theme.darkPink


@Composable
fun RegisterAlertDialog(
    showDialog: MutableState<Boolean>, // To control dialog visibility
    onConfirm: () -> Unit = {}, // Action on confirming
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false }, // Close dialog on background click

            text = {
                Column(
                    modifier = Modifier.wrapContentHeight()
                        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
                {
                    Box(modifier = Modifier.size(80.dp)
                        .shadow(elevation = 1.dp, shape = CircleShape).background(darkPink), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(R.drawable.vector_7),
                            contentDescription = null, modifier = Modifier.size(50.dp)) }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Success", style = TextStyle(fontSize = 16.sp, color = darkPink, fontWeight = FontWeight.W500))
                    Spacer(Modifier.height(4.dp))
                    Text(text = "Congratulations, you have \n" + "completed your registration!",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = BlackColor, fontWeight = FontWeight.W400, textAlign = TextAlign.Center))
                    Spacer(Modifier.height(8.dp))
                    ButtonBox(containerColor = darkPink, text = "Done", textColor = Color.White, modifier = Modifier.fillMaxWidth()) {
                        onConfirm()
                    }
                }
            },
            dismissButton = {

            },
            confirmButton = {

            },
            shape = MaterialTheme.shapes.medium, // Rounded corners
            containerColor = MaterialTheme.colorScheme.surface, // Background color
            modifier = Modifier

        )
    }
}

@Composable
fun ButtonBox(
    containerColor: Color,
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Button(
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        shape = RoundedCornerShape(8.dp), border = BorderStroke(
            width = 1.dp,
            color = darkPink
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                color = textColor,
                fontWeight = FontWeight.W500
            )
        )
    }
}
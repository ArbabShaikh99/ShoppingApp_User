package com.example.shoppingapp_user.Presentation_layer.Screens.Shiping.Component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp_user.ui.theme.BlackColor
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray


@Composable
fun ShippingMethodColumn(
    radioFreeSelected: Boolean,
    radioCashSelected: Boolean,
    radioFreeButtonOnClick: () -> Unit,
    radioCashButtonOnClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            "Shipping Method",
            fontSize = 18.sp,
            color = BlackColor,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(9.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = darkPink
                    ), shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RadioButtonWithText(
                radioSelected = radioFreeSelected,
                textMessage = "Standard FREE delivery over Rs:4500",
                charge = "Free",
                onClick = {
                    radioFreeButtonOnClick()
                }
            )
            HorizontalDivider(
                thickness = 0.5.dp,
                color = customGray
            )
            RadioButtonWithText(
                radioSelected = radioCashSelected,
                textMessage = "Cash on delivery over Rs:4500 (Free Delivery, COD processing fee only)",
                charge = "100",
                onClick = {
                    radioCashButtonOnClick()
                }
            )
        }
    }
}


@Composable
fun RadioButtonWithText(
    radioSelected: Boolean,
    textMessage: String,
    charge: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(
            selected = radioSelected,
            onClick = {
                onClick()
            },
            colors = RadioButtonDefaults.colors(
                selectedColor = darkPink,
                unselectedColor = customGray
            ), modifier = Modifier.weight(0.1f)
        )
        Text(
            text = textMessage, style = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight.W500,
                color = customGray,
            ), modifier = Modifier.weight(
                0.8f
            ), textAlign = TextAlign.Start
        )
        Text(
            text = charge, style = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight.W700,
                color = customGray,
            ), modifier = Modifier.weight(
                0.1f
            ), textAlign = TextAlign.End
        )
    }
}
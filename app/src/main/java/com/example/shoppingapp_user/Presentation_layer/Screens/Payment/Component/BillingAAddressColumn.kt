package com.example.shoppingapp_user.Presentation_layer.Screens.Payment.Component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray


@Composable
fun BillingAddressColumn(
    isRadioShippingAddressSelected: Boolean,
    isRadioDifferentAddressSelected: Boolean,
    onRadioShippingAddressSelected: () -> Unit,
    onRadioDifferentAddressSelected: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(lightGray),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Billing Address", style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.W700,
                color = customGray,
            )
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Select the address that matches your card or payment method.",
            style = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight.W400,
                color = customGray,
            )
        )
        Spacer(Modifier.height(8.dp))
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
            RadioButtonWithTextPayment(
                radioSelected = isRadioShippingAddressSelected,
                textMessage = "Same as shipping address",
                onClick = {
                    onRadioShippingAddressSelected()
                }
            )
            HorizontalDivider(
                thickness = 0.5.dp,
                color = customGray
            )
            RadioButtonWithTextPayment(
                radioSelected = isRadioDifferentAddressSelected,
                textMessage = "Use a different billing address",
                onClick = {
                    onRadioDifferentAddressSelected()
                }
            )
        }
    }
}



@Preview
@Composable
fun RadioButtonWithTextPayment(
    radioSelected: Boolean = true,
    textMessage: String = "Same as shipping address",
    onClick: () -> Unit = {}
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
            )
        )
        Text(
            text = textMessage, style = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight.W500,
                color = customGray,
            ), modifier = Modifier.weight(
                0.6f
            ), textAlign = TextAlign.Start
        )
        Text(
            text = "change", style = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight.W400,
                color = customGray,
            ), modifier = Modifier.weight(
                0.1f
            ), textAlign = TextAlign.Center
        )
    }
}

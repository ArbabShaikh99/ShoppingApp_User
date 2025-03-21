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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.CardBackGroundColor
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray


@Composable
fun PaymentCard(
    isRadioBankCardSelected: Boolean,
    isRadioCashSelected: Boolean,
    onRadioBankCardSelected: () -> Unit,
    onRadioCashSelected: () -> Unit
) {
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
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RadioBankCardRow(
            onRadioBankCardSelected = isRadioBankCardSelected,
            onRadioBankCardClick = {
                onRadioBankCardSelected()
            }
        )
        Spacer(Modifier.height(4.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBackGroundColor.copy(alpha = 0.24f))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.subtract),
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                tint = customGray
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "After clicking 'Pay Now', you will be redirected to Easypaisa to complete your payment securely."
                ,style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.W400,
                    color = customGray,
                )
            )
        }
        Spacer(Modifier.height(8.dp))
        HorizontalDivider(
            thickness = 0.5.dp,
            color = customGray
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(lightGray),
            verticalAlignment = Alignment.CenterVertically, // Align vertically to center
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // RadioButton should be aligned to the top using alignment within its modifier
            RadioButton(
                selected = isRadioCashSelected,
                onClick = {
                    onRadioCashSelected()
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = darkPink,
                    unselectedColor = customGray
                ),
                modifier = Modifier.align(Alignment.Top) // Ensure it's aligned at the top of the Row
            )

            // Text modifier remains the same
            Text(
                text = "Cash On Delivery  (COD)",
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.W500,
                    color = customGray,

                ),
                modifier = Modifier.weight(0.6f),
                textAlign = TextAlign.Start
            )
        }
    }

}

@Composable
fun RadioBankCardRow(
    onRadioBankCardSelected: Boolean,
    onRadioBankCardClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(lightGray),
        verticalAlignment = Alignment.CenterVertically, // Align vertically to center
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // RadioButton should be aligned to the top using alignment within its modifier
        RadioButton(
            selected = onRadioBankCardSelected,
            onClick = {
                onRadioBankCardClick()
            },
            colors = RadioButtonDefaults.colors(
                selectedColor = darkPink,
                unselectedColor = customGray
            ),
            modifier = Modifier.align(Alignment.Top) // Ensure it's aligned at the top of the Row
        )

        // Text modifier remains the same
        Text(
            text = "Easypaisa Payment Gateway",
            style = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight.W500,
                color = customGray,
            ),
            modifier = Modifier.weight(0.6f),
            textAlign = TextAlign.Start
        )

        // Row for icons is fine, as it seems to align properly
        Row(
            modifier = Modifier
                .weight(0.3f)
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(R.drawable.easypaisa),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )

        }

    }
}
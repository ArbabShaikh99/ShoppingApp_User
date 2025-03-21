package com.example.shoppingapp_user.Presentation_layer.Screens.Shiping.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shoppingapp_user.Domain.Models.CartModel
import com.example.shoppingapp_user.Presentation_layer.Screens.Product.ColorBox
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray
import com.example.shoppingapp_user.ui.theme.pink

@Composable
fun ShippingProductColumn(
    cartProduct: CartModel,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .height(155.dp)
            .background(lightGray)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            Card(
                modifier = Modifier
                    .height(77.dp)
                    .width(55.dp)
                    .background(lightGray),
                shape = RoundedCornerShape(8.dp)
            ) {
                AsyncImage(
                    model = cartProduct.productImageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    error = painterResource(R.drawable.ic_launcher_foreground)
                )
            }
            Spacer(Modifier.width(4.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = cartProduct.productName, style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W700,
                            color = customGray,

                            )
                    )
                    Text(
                        text = cartProduct.productFinalPrice, style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W700,
                            color = customGray,

                            )
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "GF150", style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W700,
                            color = customGray,

                            ), modifier = Modifier.weight(0.8f)
                    )
                    Text(
                        text = "x" + cartProduct.productQty, style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W700,
                            color = customGray,
                            textAlign = TextAlign.End
                        )
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = cartProduct.size, style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W500,
                            color = customGray,

                            )
                    )
                    Spacer(Modifier.width(4.dp))


                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .shadow(
                                elevation = 1.dp,
                                shape = CircleShape
                            )
                            .background(
                                darkPink)
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        HorizontalDivider(
            thickness = 0.5.dp,
            color = customGray
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Sub Total", style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.W700,
                    color = customGray,
                )
            )
            Text(
                text = cartProduct.productFinalPrice, style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.W700,
                    color = customGray,
                )
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Shipping", style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.W600,
                    color = customGray,
                )
            )
            Text(
                text = "Free", style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.W600,
                    color = customGray,

                    )
            )
        }
    }
}

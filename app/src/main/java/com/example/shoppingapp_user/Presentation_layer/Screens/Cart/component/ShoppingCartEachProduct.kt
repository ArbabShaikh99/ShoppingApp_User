package com.example.shoppingapp_user.Presentation_layer.Screens.Cart.component
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shoppingapp_user.Domain.Models.CartModel
import com.example.shoppingapp_user.ui.theme.CustomGrayShipping
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.lightGray
import com.example.shoppingapp_user.ui.theme.notificationColor


@Composable
fun ShoppingCartEachProduct(
    cartModel :CartModel,
    onRemoveClick:()-> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .background(lightGray)
            .padding(top = 16.dp , start = 18.dp, end = 7.dp , bottom = 20.dp )
    ) {
        Card(
            modifier = Modifier
                .height(105.dp)
                .width(75.dp)
                .background(lightGray),
            shape = RoundedCornerShape(8.dp)
        ) {
            AsyncImage(
                model = cartModel.productImageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top) {

            Row (modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically){

                Text(
                    text = cartModel.productName,
                    style = TextStyle(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Medium,
                        color = CustomGrayShipping,
                    ),
                    modifier = Modifier.weight(0.5f), maxLines = 3
//                    modifier = Modifier.weight(1f),
//                    maxLines = if(cartModel.productName.length>7) 2 else 1,
//                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.width(13.dp))

                Text(
                    text = "Rs: ${cartModel.productFinalPrice}",
                    style = TextStyle(
                        fontSize = 11.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Medium,
                        color = CustomGrayShipping,
                    ),
                )
                Spacer(Modifier.width(32.dp))

                Text(
                    text = cartModel.productQty,
                    style = TextStyle(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Medium,
                        color = CustomGrayShipping
                    ),
                )
                Spacer(Modifier.width(48.dp))

                Text(
                    text = (cartModel.productFinalPrice.toInt() * cartModel.productQty.toInt()).toString(),
                    style = TextStyle(
                        fontSize = 11.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Medium,
                        color = CustomGrayShipping
                    ),
                )
                Spacer(Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = CustomGrayShipping,
                    modifier = Modifier.size(22.dp).clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ){
                        onRemoveClick()
                    }
                )
            }
Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "GF10125",
                style = TextStyle(
                    fontSize = 9.sp,
                    fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Start,
                    color = notificationColor,
                ),
            )
            Spacer(Modifier.height(9.dp))
            Text(
                text = cartModel.size,
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.W400,
                    textAlign = TextAlign.Start,
                    color = CustomGrayShipping,
                ),
            )
            Spacer(Modifier.height(9.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Color: ",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.W500,
                        textAlign = TextAlign.Start,
                        color = CustomGrayShipping,
                    ),
                )
                Spacer(Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .background(Color(cartModel.color.toInt()))
                )
            }

        }
    }
}
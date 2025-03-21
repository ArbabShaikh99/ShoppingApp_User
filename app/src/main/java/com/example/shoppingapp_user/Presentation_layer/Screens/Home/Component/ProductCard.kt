package com.example.shoppingapp_user.Presentation_layer.Screens.Home.Component

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.lightGray
import com.example.shoppingapp_user.ui.theme.notificationColor
import com.example.shoppingapp_user.ui.theme.pink

@Composable
fun ProductCard(
    image :String ,
    name :String,
    productCode :String ,
    price :String,
    finalPrice :String,
    onClick :()->Unit

) {
    Column(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(top = 9.dp, start = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


//        AndroidView(
//            factory = { context ->
//                ImageView(context).apply {
//                    Glide.with(context)
//                        .load(image)
//                        .placeholder(R.drawable.ic_launcher_foreground) // Optional placeholder
//                        .error(R.drawable.ic_launcher_background) // Optional error fallback
//                        .into(this)
//                }
//            },
//            modifier = Modifier
//                .width(100.dp)
//                .height(140.dp)
//               // .padding(10.dp)
//        )

        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(100.dp)
                .height(140.dp)
                .clip(RoundedCornerShape(15.dp))
                .border(
                    width = 1.dp,
                    color = lightGray,
                    shape = RoundedCornerShape(15.dp)
                ),
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground)

              //  .placeholder(R.drawable.ic_launcher_foreground)


        )
        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier
                .height(116.dp)
                .width(100.dp)
                // .background(lightGray)
                .border(
                    width = 1.dp,
                    color = customGray,
                    shape = RoundedCornerShape(15.dp)
                ),
            colors = CardDefaults.cardColors(
                containerColor = lightGray // Yahan background color define hoga
            )
        ) {
            Column(modifier = Modifier.padding(
                top = 7.dp , start = 8.dp ),
                //horizontalAlignment = Alignment.CenterHorizontally)
            )    //.background(lightGray))
            {
                Text(
                    text = name,
                    color = customGray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis, // Agar text zyada ho to ... show karega
                    lineHeight = 13.sp,
                    //   textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    productCode , color = customGray,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Rs: ", color = pink,
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp
                    )
                    Text(
                        price, color = pink,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )

                }

                Row(modifier = Modifier.fillMaxWidth()) {

                    Text(
                        "Rs: ", color = notificationColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp
                    )
                    Text(
                         finalPrice, color = notificationColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                    Text(
                        "20% off", color = pink,
                        fontWeight = FontWeight.Medium,
                        fontSize = 8.sp
                    )
                }

            }
        }

    }

}

package com.example.shoppingapp_user.Presentation_layer.Screens.Home.Component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.customGray


@Composable
fun CategoryCard(
    ImageOfCategory: String,
    CategoryName :String,
    onclick :() -> Unit
){

    Column(
        modifier = Modifier
            .padding(top = 11.dp, start = 23.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Center align items
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(65.dp)
                .border(width = 1.dp, color = customGray, shape = CircleShape)
                .clickable {
                    onclick()
                }

        ) {
            AsyncImage(
                model = ImageOfCategory,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
                    .clip(
                        RoundedCornerShape(20.dp)
                    )
            )
        }

        //Spacer(modifier = Modifier.height(5.dp)) // Space between Box & Text

        Text(

            text = if(CategoryName.length > 15)
                CategoryName.take(15)+".."
            else CategoryName,
            color = customGray,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,

        )
    }
}



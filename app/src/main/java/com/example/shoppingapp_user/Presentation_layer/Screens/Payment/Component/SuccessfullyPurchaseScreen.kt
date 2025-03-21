package com.example.shoppingapp_user.Presentation_layer.Screens.Payment.Component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.navigation.NavHostController
import com.example.shoppingapp_user.Presentation_layer.Navigation.ScreenNavigation.Routes
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray


@Composable
fun SuccessfullyPurchaseScreen(navController: NavHostController) {
    BackHandler {
        navController.navigate(Routes.HomeRoutes){
            popUpTo(Routes.HomeRoutes){
                inclusive = true
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().background(lightGray).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.frame),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Successful purchase!",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                color = customGray,
            ),
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(8.dp))
        ButtonComponent(
            text = "Continue Shopping",
        ) {
            navController.navigate(Routes.HomeRoutes){
                popUpTo(Routes.HomeRoutes){
                    inclusive = true
                }
            }
        }
    }
}




@Composable
fun ButtonComponent(
    text : String,
    containerColor : Color = darkPink,
    onClick:()->Unit
) {

    Button(
        onClick = {
            onClick()
        }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = lightGray
        ), shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Start,
                color = lightGray,
            )
        )
    }

}
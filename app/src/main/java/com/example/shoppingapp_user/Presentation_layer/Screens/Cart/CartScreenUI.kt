package com.example.shoppingapp_user.Presentation_layer.Screens.Cart

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shoppingapp_user.Presentation_layer.Navigation.ScreenNavigation.Routes
import com.example.shoppingapp_user.Presentation_layer.Screens.Cart.component.ShoppingCartEachProduct
import com.example.shoppingapp_user.Presentation_layer.Screens.Cart.component.sumAllTotalPrice
import com.example.shoppingapp_user.Presentation_layer.ViewModel.CartViewModel
import com.example.shoppingapp_user.ui.theme.CustomGray1
import com.example.shoppingapp_user.ui.theme.CustomGrayShipping
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun CartScreenUI (
    cartViewModel: CartViewModel = hiltViewModel(),
    navController: NavController
) {

   val getAllCartProductResponseState = cartViewModel.getAllProductInCartState.collectAsState()
    // Add One Product and delete on product in data base contain a data class of getAllProductModel
    val productInCartPerformResponseState = cartViewModel.cartProductState.collectAsState()

    val context = LocalContext.current
    val coroutines = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutines.launch(Dispatchers.IO) {
            cartViewModel.getAllProductInCart()
        }
    }
    if(productInCartPerformResponseState.value.isLoading){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                color = darkPink, modifier = Modifier.size(45.dp))
        }
    }
    else if (productInCartPerformResponseState.value.data.isNotBlank()){
       cartViewModel.getAllProductInCart()
        cartViewModel.resetProductInCartState()
        Toast.makeText(context, productInCartPerformResponseState.value.data, Toast.LENGTH_SHORT).show()
    }
    else if (productInCartPerformResponseState.value.error.isNotBlank()){
        cartViewModel.getAllProductInCart()
        cartViewModel.resetProductInCartState()
        Toast.makeText(context , productInCartPerformResponseState.value.error ,Toast.LENGTH_SHORT ).show()
    }

    if(getAllCartProductResponseState.value.isLoading){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                color = darkPink, modifier = Modifier.size(45.dp))
        }
    }
    else if (getAllCartProductResponseState.value.data.isNotEmpty()){
        val cartList = getAllCartProductResponseState.value.data


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(1000.dp) // Ensure Box has height
            ) {
                // Circle Shape (Top-Right Side)
                Canvas(
                    modifier = Modifier
                        .size(180.dp)
                        .offset(x = 250.dp, y = -60.dp)
                ) {
                    drawCircle(color = darkPink)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(lightGray)
                ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 65.dp, start = 18.dp)
                ) {

                    Text(
                        text = "Shopping Cart",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 23.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = customGray,
                            modifier = Modifier
                                .size(23.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    navController.popBackStack()
                                }
                        )

                        Spacer(modifier = Modifier.width(11.dp))
                        Text(
                            text = "Continue Shopping ",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = customGray
                        )
                    }
                }
               // Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 15.dp, start = 18.dp),
                    ) {
                        Text(
                            text = "Items",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Start,
                                color = CustomGrayShipping,

                            ), modifier = Modifier.weight(0.8f)
                        )
                        Text(
                            text = "Price",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Start,
                                color = CustomGrayShipping,
                            ), modifier = Modifier.weight(0.3f)
                        )
                        Text(
                            text = "Qty",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Start,
                                color = CustomGrayShipping,
                            ), modifier = Modifier.weight(0.3f)
                        )
                        Text(
                            text = "Total",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W600,
                                textAlign = TextAlign.Start,
                                color = CustomGrayShipping,
                            ), modifier = Modifier.weight(0.3f)
                        )
                    }
                    LazyColumn (modifier = Modifier.fillMaxWidth().wrapContentHeight()) {

                        items(cartList) {
                            ShoppingCartEachProduct(it,{
                                cartViewModel.CardProduct(it)
                            }

                            )
                        }
                        item {
                            Spacer(Modifier.height(16.dp))
                            HorizontalDivider(
                                thickness = 0.5.dp,
                                color = CustomGrayShipping
                            )
                            Spacer(Modifier.height(16.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 16.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Sub Total",
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.W600,
                                        textAlign = TextAlign.Start,
                                        color = Color.Black,
                                        fontFamily = FontFamily.SansSerif
                                    )
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "Rs: ${sumAllTotalPrice(cartModel = cartList)}",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W600,
                                        textAlign = TextAlign.Start,
                                        color = customGray,
                                        fontFamily = FontFamily.SansSerif
                                    )
                                )
                            }
                            Spacer(Modifier.height(15.dp))

                            Column(modifier = Modifier.fillMaxWidth().padding(
                                start = 40.dp , top  = 23.dp )){
                            Button(
                                onClick = {
                                    navController.navigate(Routes.shippingRoute(
                                        productId = "",
                                        productQty = "",
                                        color = "",
                                        size = ""
                                    ))

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = CustomGray1), // ✅ Corrected
                                shape = RoundedCornerShape(17.dp), // ✅ Corners rounded
                                modifier = Modifier
                                    .width(320.dp)
                                    .height(50.dp)
                            ) {
                                Text(  "Check Out", color = Color.White, fontSize = 16.sp)
                            }
                        }
                        }


                        }



            }
        }


    }
    else {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) { Text(text = "No Data Found") }
    }
}
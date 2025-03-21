package com.example.shoppingapp_user.Presentation_layer.Screens.Payment

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shoppingapp_user.Domain.Models.CartModel
import com.example.shoppingapp_user.Domain.Models.OrderModel
import com.example.shoppingapp_user.Domain.Models.toCartModel
import com.example.shoppingapp_user.MainActivity
import com.example.shoppingapp_user.Presentation_layer.Navigation.ScreenNavigation.Routes
import com.example.shoppingapp_user.Presentation_layer.Screens.Cart.component.sumAllTotalPrice
import com.example.shoppingapp_user.Presentation_layer.Screens.Payment.Component.BillingAddressColumn
import com.example.shoppingapp_user.Presentation_layer.Screens.Payment.Component.PaymentCard
import com.example.shoppingapp_user.Presentation_layer.Screens.Shiping.Component.ShippingProductColumn
import com.example.shoppingapp_user.Presentation_layer.ViewModel.CartViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.GetShippingDataState
import com.example.shoppingapp_user.Presentation_layer.ViewModel.OrderViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.ShippingViewModel
import com.example.shoppingapp_user.ui.theme.BlackColor
import com.example.shoppingapp_user.ui.theme.CustomGray1
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray
import kotlinx.coroutines.delay

@Composable
fun PaymentScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel = hiltViewModel(),
    shippingViewModel: ShippingViewModel = hiltViewModel(),
    navController: NavController
) {
    val getAllCartProductResponseState = cartViewModel.getAllProductInCartState.collectAsState()
    val cartProductList = getAllCartProductResponseState.value.data
    val context = LocalContext.current
   // val cntx = LocalContext.current as MainActivity

       val orderAddResponseState = orderViewModel.orderState.collectAsState()
       val paymentResponseState = orderViewModel.paymentState.collectAsState()
       val shippindDataState = shippingViewModel.getShippingState.collectAsState()

    var isRadioBankCardSelected by rememberSaveable {
        mutableStateOf(false)
    }
    var isRadioCashSelected by rememberSaveable {
        mutableStateOf(false)
    }
    var isRadioShippingAddressSelected by rememberSaveable {
        mutableStateOf(false)
    }
    var isRadioDifferentAddressSelected by rememberSaveable {
        mutableStateOf(false)
    }

    if (getAllCartProductResponseState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = darkPink)
        }
    } else if (getAllCartProductResponseState.value.error.isNotEmpty()) {
        Toast.makeText(context, getAllCartProductResponseState.value.error, Toast.LENGTH_SHORT)
            .show()
    } else {



        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(lightGray)
                .padding(start = 17.dp, top = 48.dp, end = 21.dp),
        ) {
            Text("Payments", fontSize = 25.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        navController.navigateUp()
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = "Back",
                    tint = customGray,
                    modifier = Modifier
                        .size(21.dp)
                )
                Text(
                    "Return to Shipping",
                    fontSize = 16.sp,
                    color = customGray,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(8.dp))
            if (cartProductList.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    items(cartProductList) { cartProduct ->
                        if (cartProductList.size > 1) {
                            ShippingProductColumn(
                                cartProduct = cartProduct,
                                modifier = Modifier.width(290.dp)
                            )
                        } else {
                            ShippingProductColumn(
                                cartProduct = cartProduct,
                                modifier = Modifier.width(350.dp)
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(4.dp))
            HorizontalDivider(
                thickness = 0.5.dp,
                color = customGray,
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total", style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W700,
                        color = BlackColor,
                    )
                )
                Text(
                    text = "Rs: " + sumAllTotalPrice(cartProductList), style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W700,
                        color = BlackColor,
                    )
                )
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = customGray,
            )

            Text(
                "Shipping Method",
                fontSize = 17.sp,
                color = BlackColor,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "All Transaction secure and encrypted", style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W500,
                    color = customGray,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            PaymentCard(
                isRadioBankCardSelected = isRadioBankCardSelected,
                isRadioCashSelected = isRadioCashSelected,
                onRadioBankCardSelected = {
                    isRadioBankCardSelected = !isRadioBankCardSelected
                    if (isRadioBankCardSelected) {
                        isRadioCashSelected = false
                    }
                },
                onRadioCashSelected = {
                    isRadioCashSelected = !isRadioCashSelected
                    if (isRadioCashSelected) {
                        isRadioBankCardSelected = false
                    }
                }
            )

            BillingAddressColumn(
                isRadioShippingAddressSelected = isRadioShippingAddressSelected,
                isRadioDifferentAddressSelected = isRadioDifferentAddressSelected,
                onRadioShippingAddressSelected = {
                    isRadioShippingAddressSelected = !isRadioShippingAddressSelected
                    if (isRadioShippingAddressSelected) {
                        isRadioDifferentAddressSelected = false
                    }
                },
                onRadioDifferentAddressSelected = {
                    isRadioDifferentAddressSelected = !isRadioDifferentAddressSelected
                    if (isRadioDifferentAddressSelected) {
                        isRadioShippingAddressSelected = false
                    }
                }
            )
            Spacer(Modifier.height(8.dp))

            Button(
              onClick = {
                  if(isRadioBankCardSelected){
                           Toast.makeText(context ,"Radio bank selected " , Toast.LENGTH_SHORT).show()
                  }
                  else if (isRadioCashSelected) {
                      orderCreate(
                          shippingDataState = shippindDataState,
                          cartProductList = cartProductList,
                          orderViewmodel = orderViewModel,
                      )
                      createNotificationChannel(context)
                      sendNotification(context)

                  }

              },
                colors = ButtonDefaults.buttonColors(containerColor = CustomGray1), // ✅ Corrected
                shape = RoundedCornerShape(17.dp), // ✅ Corners rounded
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
            ) {
                Text("Pay now", color = Color.White, fontSize = 16.sp)
            }


        }
    }


  // For Cash On deliery
    when {
        orderAddResponseState.value.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Log.d("@pay", "PaymentScreen Loading...")
                CircularProgressIndicator(color = darkPink)
            }
        }

        orderAddResponseState.value.error.isNotEmpty() -> {
            Log.d("@pay", "PaymentScreen error: ${orderAddResponseState.value.error}")
            Toast.makeText(context, orderAddResponseState.value.error, Toast.LENGTH_SHORT).show()



        }

        orderAddResponseState.value.data.isNotEmpty() -> {
            Log.d("@pay", "PaymentScreen data: ${orderAddResponseState.value.data}")
            Toast.makeText(context, orderAddResponseState.value.data, Toast.LENGTH_SHORT).show()
            LaunchedEffect(orderAddResponseState.value) {
                if (orderAddResponseState.value.data.isNotEmpty()) {
                    Log.d("@pay44", "Navigating after data received")
                    delay(4000)
                    orderViewModel.clearOrderState()
                    navController.navigate(Routes.SuccesslfullyPurchaseRoute)
                }
            }

        }
    }


    // Online payment
    when {
        paymentResponseState.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center) {
                Log.d("@pay", "PaymentScreen Loading...")
                CircularProgressIndicator(color = darkPink)
            }
        }

        paymentResponseState.value.paymentState.isNotEmpty() -> {
            Log.d("@pay", "PaymentScreen data: ${paymentResponseState.value.paymentState}")
            orderCreate(
                shippingDataState = shippindDataState,
                cartProductList = cartProductList,
                orderViewmodel = orderViewModel,
                transactionId = paymentResponseState.value.paymentState,
                transactionMethod = "Online Payment"
            )
            createNotificationChannel(context)
            sendNotification(context)
            orderViewModel.clearPaymentState()
        }

        paymentResponseState.value.error.isNotEmpty() -> {
            Log.d("@pay", "PaymentScreen error: ${paymentResponseState.value.error}")
            Toast.makeText(context, paymentResponseState.value.error, Toast.LENGTH_SHORT).show()
            orderViewModel.clearPaymentState()
        }
    }

}



fun orderCreate(
    shippingDataState: State<GetShippingDataState>,
    cartProductList: List<CartModel>,
    orderViewmodel: OrderViewModel,
    transactionId: String = "Cash On Delivery",
    transactionMethod: String = "Cash On Delivery"
) {
    val orderList = mutableListOf<OrderModel>()
    if (shippingDataState.value.data != null) {
        val shippingData = shippingDataState.value.data!!
        for (cartProduct in cartProductList) {
            val orderModel = OrderModel(
                orderId = "O_" + System.currentTimeMillis(),
                productId = cartProduct.productId,
                productName = cartProduct.productName,
                productDescription = cartProduct.productDescription,
                productQty = cartProduct.productQty,
                productFinalPrice = cartProduct.productFinalPrice,
                productImageUrl = cartProduct.productImageUrl,
                color = cartProduct.color,
                size = cartProduct.size,
                productPrice = cartProduct.productPrice,
                totalPrice = ((cartProduct.productFinalPrice.toInt() * cartProduct.productQty.toInt()).toString()),
                userAddress = shippingData.address,
                userEmail = shippingData.email,
                firstName = shippingData.firstName,
                lastName = shippingData.lastName,
                postalCode = shippingData.postalCode,
                productCategory = cartProduct.productCategory,
                transactionMethod = transactionMethod,
                city = shippingData.city,
                countryRegion = shippingData.countryRegion,
                mobileNo = shippingData.mobileNo,
                transactionId = transactionId
            )
            orderList.add(orderModel)
        }
        orderViewmodel.orderDataSave(
            orderList = orderList
        )
    }
}
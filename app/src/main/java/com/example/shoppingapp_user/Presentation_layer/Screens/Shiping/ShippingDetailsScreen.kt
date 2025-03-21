package com.example.shoppingapp_user.Presentation_layer.Screens.Shiping


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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shoppingapp_user.Domain.Models.CartModel
import com.example.shoppingapp_user.Domain.Models.ShippingModel
import com.example.shoppingapp_user.Domain.Models.toCartModel
import com.example.shoppingapp_user.Presentation_layer.Navigation.ScreenNavigation.Routes
import com.example.shoppingapp_user.Presentation_layer.Screens.Cart.component.sumAllTotalPrice
import com.example.shoppingapp_user.Presentation_layer.Screens.Shiping.Component.ShipingTextField
import com.example.shoppingapp_user.Presentation_layer.Screens.Shiping.Component.ShippingMethodColumn
import com.example.shoppingapp_user.Presentation_layer.Screens.Shiping.Component.ShippingProductColumn
import com.example.shoppingapp_user.Presentation_layer.ViewModel.CartViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.ProductViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.ShippingViewModel
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.BlackColor
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingDetailsScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    productViewModel: ProductViewModel = hiltViewModel(),
    shippingViewModel: ShippingViewModel = hiltViewModel(),
    navController: NavController,
    productID :String,
    productQty :String,
    size :String,
    color:  String
) {

    val getAllCartProductResponseState = cartViewModel.getAllProductInCartState.collectAsState()
    val  producyByIDResponseState = productViewModel.getProductByIDState.collectAsState()

    val shippingDataSaveResponseState = shippingViewModel.shippingState.collectAsState()
    val NextTimeSaveShippingDataResponseState  = shippingViewModel.getShippingState.collectAsState()

    val shippingScreenState = shippingViewModel.shippingScreenState.collectAsState().value
    val context = LocalContext.current

    val coroutine = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutine.launch (Dispatchers.IO){
            shippingViewModel.getShippingDataThroughUID()
           if(productID.isNotEmpty()){
               productViewModel.getProductByID(productID)
           }

        }
    }

    var isRadioCashSelected by rememberSaveable {
        mutableStateOf(false)
    }
    var isRadioFreeSelected by rememberSaveable {
        mutableStateOf(false)
    }


    if (getAllCartProductResponseState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = darkPink)
        }
    } else if (getAllCartProductResponseState.value.error.isNotEmpty()) {
        Toast.makeText(context, getAllCartProductResponseState.value.error, Toast.LENGTH_SHORT).show()
    } else {
        val cartProductList = getAllCartProductResponseState.value.data


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(lightGray)
                .padding(start = 17.dp, top = 48.dp, end = 21.dp),
        ) {
            Text("Shipping", fontSize = 25.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                .clickable(
                        interactionSource = remember { MutableInteractionSource() }, indication = null
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
                    "Return to Cart",
                    fontSize = 16.sp,
                    color = customGray,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(4.dp))
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
            } else {
                val productData = producyByIDResponseState.value.data?.toCartModel(
                    productQty = productQty,
                    color = color,
                    size = size
                )
                if (productData != null)
                    ShippingProductColumn(productData, modifier = Modifier.fillMaxWidth())
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
                    text = if (cartProductList.isNotEmpty()) {
                        "Rs: "+sumAllTotalPrice(cartProductList)
                    } else {
                        "Rs: "+(producyByIDResponseState.value.data?.finalprice?.toInt()
                            ?.times(productQty.toInt()))
                    }, style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W700,
                        color = BlackColor,
                        //fontFamily = FontFamily(Font(R.font.montserrat_bold))
                    )
                )
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = customGray,
            )





            Spacer(modifier = Modifier.height(12.dp))
            // Contact Information
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Contact Information",
                    fontSize = 18.sp,
                    color = BlackColor,
                    fontWeight = FontWeight.Medium
                )


            }

            Spacer(modifier = Modifier.height(8.dp))

            ShipingTextField(
                value = shippingScreenState.email.value,
                valueChange = { shippingScreenState.email.value = it },
                Placeholder = "Email",
                widthDefine = 355.dp
            )

            Spacer(modifier = Modifier.height(12.dp))

            ShipingTextField(
                value = shippingScreenState.mobileNo.value,
                valueChange = { shippingScreenState.mobileNo.value = it },
                Placeholder = "Contact number",
                widthDefine = 355.dp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Shipping Address",
                fontSize = 18.sp,
                color = BlackColor,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            ShipingTextField(
                value = shippingScreenState.countryRegion.value,
                valueChange = { shippingScreenState.countryRegion.value = it },
                Placeholder = "Country/Region",
                widthDefine = 355.dp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ShipingTextField(
                    value =shippingScreenState.firstName.value ,
                    valueChange = { shippingScreenState.firstName.value = it },
                    Placeholder = "First Name",
                    widthDefine = 160.dp
                )


                ShipingTextField(
                    value = shippingScreenState.lastName.value,
                    valueChange = { shippingScreenState.lastName.value = it },
                    Placeholder = "Last Name",
                    widthDefine = 160.dp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            ShipingTextField(
                value = shippingScreenState.address.value,
                valueChange = { shippingScreenState.address.value = it },
                Placeholder = "Address",
                widthDefine = 355.dp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                ShipingTextField(
                    value = shippingScreenState.city.value,
                    valueChange = { shippingScreenState.city.value = it },
                    Placeholder = "City",
                    widthDefine = 160.dp
                )

                ShipingTextField(
                    value = shippingScreenState.postalCode.value,
                    valueChange = { shippingScreenState.postalCode.value = it },
                    Placeholder = "Postal Code",
                    widthDefine = 160.dp
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Checkbox(
                    checked = shippingScreenState.isSaveForNextTime.value,
                    onCheckedChange = {shippingScreenState.isSaveForNextTime.value =
                        !shippingScreenState.isSaveForNextTime.value},

                    colors = CheckboxDefaults.colors(
                        checkedColor = darkPink,
                        uncheckedColor = customGray,
                        disabledUncheckedColor = customGray
                    ),
                    modifier = Modifier
                        .size(12.dp)
                        .background(lightGray)
                        .shadow(
                            elevation = 0.dp,
                            shape = RoundedCornerShape(8.dp)
                        )
                )
                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Save this information for next time", style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.W400,
                        color = customGray,

                    )
                )

            }


            Spacer(modifier = Modifier.height(14.dp))


            // Shipping Method
            ShippingMethodColumn(
                radioFreeSelected = isRadioFreeSelected,
                radioCashSelected = isRadioCashSelected,
                radioCashButtonOnClick = {
                    isRadioCashSelected = !isRadioCashSelected
                    if (isRadioCashSelected)
                        isRadioFreeSelected = false
                },
                radioFreeButtonOnClick = {
                    isRadioFreeSelected = !isRadioFreeSelected
                    if (isRadioFreeSelected)
                        isRadioCashSelected = false
                }
            )


            Spacer(modifier = Modifier.height(16.dp))

            // Continue Button
            Button(
                onClick = {

                    val shoppingModel = ShippingModel(
                        email = shippingScreenState.email.value,
                        mobileNo = shippingScreenState.mobileNo.value,
                        countryRegion = shippingScreenState.countryRegion.value,
                        firstName = shippingScreenState.firstName.value,
                        lastName = shippingScreenState.lastName.value,
                        address = shippingScreenState.address.value,
                        city = shippingScreenState.city.value,
                        postalCode = shippingScreenState.postalCode.value,
                        saveForNextTime = shippingScreenState.isSaveForNextTime.value
                    )
                    val error = validateShippingModel(shippingModel = shoppingModel)

                    if (error.isEmpty()) {
                        shippingViewModel.shippingDataSave(shoppingModel)
                       navController.navigate(Routes.PaymentRoute)
                    } else {
                        error.forEach {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    }

                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text("Continue to Shipping", color = Color.White)
            }

            Spacer(modifier = Modifier.height(70.dp))
        }
    }


    when {
        shippingDataSaveResponseState.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = darkPink)
            }
        }

        shippingDataSaveResponseState.value.data.isNotEmpty() -> {
            Log.d("@shipping", "ShippingScreen: ${shippingDataSaveResponseState.value.data}")
        }

        shippingDataSaveResponseState.value.error.isNotEmpty() -> {
            Log.d("@shipping", "ShippingScreen: ${shippingDataSaveResponseState.value.error}")
            Toast.makeText(context, shippingDataSaveResponseState.value.error, Toast.LENGTH_SHORT).show()
        }
    }
    if (producyByIDResponseState.value.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f)), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = darkPink) } }

    when {
        NextTimeSaveShippingDataResponseState.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize().background(color = Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = darkPink)
            }
        }

        // Next Time Save the Shippping Address of check boss
        NextTimeSaveShippingDataResponseState.value.data != null -> {
            Log.d("@payment", "ShippingScreen: ${NextTimeSaveShippingDataResponseState.value.data?.saveForNextTime}")
            if (NextTimeSaveShippingDataResponseState.value.data!!.saveForNextTime) {
                shippingScreenState.email.value =
                    NextTimeSaveShippingDataResponseState.value.data?.email.toString()
                shippingScreenState.mobileNo.value =
                    NextTimeSaveShippingDataResponseState.value.data?.mobileNo.toString()
                shippingScreenState.countryRegion.value =
                    NextTimeSaveShippingDataResponseState.value.data?.countryRegion.toString()
                shippingScreenState.firstName.value =
                    NextTimeSaveShippingDataResponseState.value.data?.firstName.toString()
                shippingScreenState.lastName.value =
                    NextTimeSaveShippingDataResponseState.value.data?.lastName.toString()
                shippingScreenState.address.value =
                    NextTimeSaveShippingDataResponseState.value.data?.address.toString()
                shippingScreenState.city.value = NextTimeSaveShippingDataResponseState.value.data?.city.toString()
                shippingScreenState.postalCode.value =
                    NextTimeSaveShippingDataResponseState.value.data?.postalCode.toString()
                shippingScreenState.isSaveForNextTime.value =
                    NextTimeSaveShippingDataResponseState.value.data?.saveForNextTime!!
            }
        }

        NextTimeSaveShippingDataResponseState.value.error.isNotEmpty() -> {
            Toast.makeText(context, NextTimeSaveShippingDataResponseState.value.error, Toast.LENGTH_SHORT).show()
        }
    }
}


fun validateShippingModel(shippingModel: ShippingModel): List<String> {
    val errors = mutableListOf<String>()

    if (shippingModel.email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(shippingModel.email)
            .matches()
    ) {
        errors.add("Invalid email address.")
    }

    if (shippingModel.mobileNo.isBlank() || shippingModel.mobileNo.length != 10 || !shippingModel.mobileNo.all { it.isDigit() }) {
        errors.add("Invalid mobile number. It must be 10 digits.")
    }

    if (shippingModel.countryRegion.isBlank()) {
        errors.add("Country/Region cannot be empty.")
    }

    if (shippingModel.firstName.isBlank()) {
        errors.add("First name cannot be empty.")
    }

    if (shippingModel.lastName.isBlank()) {
        errors.add("Last name cannot be empty.")
    }

    if (shippingModel.address.isBlank()) {
        errors.add("Address cannot be empty.")
    }

    if (shippingModel.city.isBlank()) {
        errors.add("City cannot be empty.")
    }

    if (shippingModel.postalCode.isBlank() || shippingModel.postalCode.length < 4 || shippingModel.postalCode.length > 10) {
        errors.add("Invalid postal code. It must be between 4 and 10 characters.")
    }

    return errors
}


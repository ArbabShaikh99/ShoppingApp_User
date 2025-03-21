package com.example.shoppingapp_user.Presentation_layer.Screens.Product

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shoppingapp_user.Domain.Models.getAllProductsModel
import com.example.shoppingapp_user.Domain.Models.toCartModel
import com.example.shoppingapp_user.Presentation_layer.Navigation.ScreenNavigation.Routes
import com.example.shoppingapp_user.Presentation_layer.ViewModel.CartViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.ProductViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.WishlistViewModel
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.CustomGray1
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray
import com.example.shoppingapp_user.ui.theme.notificationColor
import com.example.shoppingapp_user.ui.theme.pink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AllDetailProductScreen(
    productViewModel: ProductViewModel = hiltViewModel(),
    wishlistViewModel: WishlistViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
   navController: NavController,
    productID : String
) {

    val getSpecificProductResponseState = productViewModel.getProductByIDState.collectAsState()

    val favouiteProductResponseState = wishlistViewModel.favouriteProductState.collectAsState()
    val favouiteProduct_or_NotResponseState = wishlistViewModel.checkProductInFavouriteOrNotState.collectAsState()

    val   checkProductCartorNotResponseState = cartViewModel.productInCartorNotState.collectAsState()

    // Add One Product and delete on product in data base contain a data class of getAllProductModel
    val   ProductAddInCartResponseState = cartViewModel.cartProductState.collectAsState()
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {

            productViewModel.getProductByID(productID)
            wishlistViewModel.checkProductInFavouriteOrNot(productID)
            cartViewModel.ProductInCartorNot(productID)
        }
    }

    var sizeSelected by rememberSaveable {
        mutableStateOf("")
    }
    var colorSelected by rememberSaveable {
        mutableIntStateOf(Color.Transparent.toArgb())
    }
    var count by rememberSaveable {
        mutableIntStateOf(1)
    }

    val colorList = listOf(
        Color.Blue,
        darkPink,
        Color.Green,
        Color.Yellow
    )

    val sizeList = listOf(
        "UK 10",
        "UK 11",
        "UK 12",
        "UK 13",
    )
    // Favorite Product State in Wish List
    if(favouiteProductResponseState.value.data.isNotBlank()){
               wishlistViewModel.checkProductInFavouriteOrNot(productID)
                wishlistViewModel.resetProductInFavouite()
        Toast.makeText(context, favouiteProductResponseState.value.data, Toast.LENGTH_SHORT).show()
            }
    else if(favouiteProductResponseState.value.error.isNotEmpty()){
        Toast.makeText(context, favouiteProductResponseState.value.error, Toast.LENGTH_SHORT)
            .show()
        wishlistViewModel.resetProductInFavouite()
        //
    }

    // Product Add In Cart State
    if(ProductAddInCartResponseState.value.data.isNotBlank()){
        cartViewModel.ProductInCartorNot(productID)
        cartViewModel.resetProductInCartState()
        Toast.makeText(context, ProductAddInCartResponseState.value.data, Toast.LENGTH_SHORT)
            .show()
    }
    else if (ProductAddInCartResponseState.value.error.isNotBlank()){
        cartViewModel.resetProductInCartState()
        Toast.makeText(context, ProductAddInCartResponseState.value.error, Toast.LENGTH_SHORT)
            .show()
    }


    when {
        getSpecificProductResponseState.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = notificationColor,
                    modifier = Modifier.size(45.dp)
                )
            }
        }

        getSpecificProductResponseState.value.error != null -> {
            Text(text = getSpecificProductResponseState.value.error!!)
        }

        getSpecificProductResponseState.value.data != null -> {
          //  val productData = getSpecificProductResponseState.value.data!!
            val productList =
     getSpecificProductResponseState.value.data!!.copy(productId = productID)


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(lightGray)
                    .verticalScroll(rememberScrollState())
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(464.dp)
                ) {
                    AsyncImage(
//                painter = painterResource(R.drawable.productimage1),
                        model = productList.image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(464.dp)

                    )
                    // Gradient Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding( top = 350.dp)
                            .height(170.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.White.copy(alpha = 0.99f)),
                                    startY = 100f,
                                    endY = Float.POSITIVE_INFINITY
                                ))
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 30.dp, start = 22.dp)
                            .clickable {
                                navController.popBackStack() }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp, 40.dp) // Rectangle size
                                .background(
                                    Color.White,
                                    shape = RoundedCornerShape(8.dp))
                                .padding(8.dp)


                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
                                contentDescription = "Back",
                                tint = Color.Black, // Icon color remains white
                                modifier = Modifier.align(Alignment.Center)
                                    .size(24.dp)// Centering the icon inside the box
                            )
                        }
                    }


                    Column(
                        modifier = Modifier
                            .padding(top = 360.dp, start = 30.dp)
                    ) {

                        Text(
                            productList.name, fontSize = 20.sp,
                            fontWeight = FontWeight.W700,
                            color = Color.White
                        )
                        //  Spacer(Modifier.height(8.dp))
                        Image(
                            painter = painterResource(R.drawable.rate),
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        //  Spacer(Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "Rs.", color = Color.Black,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            )
                            Spacer(Modifier.width(7.dp))

                            Text(
                                productList.finalprice, color = Color.Black,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 29.sp
                            )

                        }



                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 23.dp, end = 23.dp),
                            horizontalArrangement = Arrangement.SpaceBetween // Ensures items are at opposite ends
                        ) {
                            Text(
                                "Size", color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )

                            Text(
                                "See more", color = darkPink,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        navController.navigate(Routes.SeeAllProductRoute)
                                    }
                            )    // Right side
                        }

                    }
                }
               // val scroll = rememberScrollState()
                Column(
                    modifier = Modifier
                        //.verticalScroll(scroll)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        LazyRow(
                            modifier = Modifier
                                .padding(start = 18.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            items(sizeList) { size ->
                                SizeBox(
                                    text = size,
                                    isSelected = size == sizeSelected // Check if the box is selected
                                ) {
                                    sizeSelected = size // Update the selected size
                                }

                            }
                        }
                        Row(
                            modifier = Modifier
                                .padding(end = 13.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.baseline_add_24),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(29.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                    count++
                                    }
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            SizeBox(
                                text = count.toString(), isSelected = false
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            Icon(
                                painter = painterResource(R.drawable.minus),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable (
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ){
                                        if(count>1)
                                            count--
                                    }
                            )
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp, top = 4.dp, end = 18.dp)
                    ) {

                        Text(
                            "Color", color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp
                        )

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {

                            items(colorList) {
                                ColorBox(
                                    color = it,
                                    colorSelected = it.toArgb() == colorSelected
                                ) {
                                    colorSelected = it.toArgb()
                                }
                            }
                        }
                        Text(
                            "Specification", color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = if (productList.description.length > 150)
                                productList.description.take(150) + "...."
                            else
                                productList.description,
                            color = customGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                        )

                    }
                    Column(modifier = Modifier.padding(start = 18.dp)) {

                        Button(
                            onClick = {


                                if (colorSelected != Color.Transparent.toArgb() && sizeSelected.isNotEmpty()) {
                                    navController.navigate(Routes.shippingRoute(
                                        productId = productID,
                                        productQty = count.toString(),
                                        color = colorSelected.toString(),
                                        size = sizeSelected

                                    ))


                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please select color and size",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = darkPink), // ✅ Corrected
                            shape = RoundedCornerShape(17.dp), // ✅ Corners rounded
                            modifier = Modifier
                                .width(320.dp)
                                .height(50.dp)
                        ) {
                            Text("Buy now", color = Color.White, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.height(13.dp))



                        // Add to Cart Button
                        if (ProductAddInCartResponseState.value.isLoading) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = darkPink,
                                    modifier = Modifier.size(45.dp)
                                )
                            }
                        } else {
                            Button(
                                onClick = {
                                    if (checkProductCartorNotResponseState.value.data) {
                                        navController.navigate(Routes.CartScreenRoute)
                                    } else {
                                        if (colorSelected != Color.Transparent.toArgb() && sizeSelected.isNotEmpty()) {
                                            val cartModel = productList.toCartModel(
                                                productQty = count.toString(),
                                                color = colorSelected.toString(),
                                                size = sizeSelected
                                            )
                                            cartViewModel.CardProduct(cartModel)


                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Please select color and size",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = CustomGray1), // ✅ Corrected
                                shape = RoundedCornerShape(17.dp), // ✅ Corners rounded
                                modifier = Modifier
                                    .width(320.dp)
                                    .height(50.dp)
                            ) {
                                Text(
                                    if (checkProductCartorNotResponseState.value.data) "Go to Cart" else "Add to Cart",
                                    color = Color.White, fontSize = 16.sp
                                )
                            }
                        }

                    }


                    val favouriteState = favouiteProduct_or_NotResponseState.value.data
                    val isLoading = favouiteProductResponseState.value.isLoading


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 18.dp , bottom = 60.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = darkPink,
                                modifier = Modifier.size(45.dp)
                            )
                        } else {

                            val icon = if (favouriteState) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                            val text = if (favouriteState) "Your Favourite" else "Add to Favourite"

                            Icon(
                                imageVector = icon,
                                contentDescription = "favorite",
                                modifier = Modifier.size(24.dp),
                                tint = darkPink
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text,
                                color = darkPink,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {

                                    wishlistViewModel.FavouriteProduct(productList)
                                }
                            )

                        }
                    }
                   // Spacer(modifier = Modifier.height(30.dp))
                }

            }
        }
    }
}






    @Composable
    fun SizeBox(
        text: String,
        isSelected: Boolean,
        onClick: () -> Unit = {}
    ) {
        Box(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onClick()
                }
                .padding(top = 4.dp, end = 8.dp, bottom = 8.dp)
                .size(40.dp)
                .background(
                    if (isSelected) pink else lightGray, shape = RoundedCornerShape(
                        8.dp
                    )
                )
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = darkPink
                    ),
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.End,
                    color = if (isSelected) lightGray else notificationColor,
                    // fontFamily = FontFamily(Font(R.font.montserrat_medium))
                )
            )
        }
    }


@Composable
fun ColorBox(
    color: Color,
    colorSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            }
            .padding(top = 2.dp, end = 8.dp, bottom = 8.dp)
            .size(40.dp)
            .background(
                color, shape = RoundedCornerShape(
                    8.dp
                )
            )
            .border(
                width = 2.dp,
                color = if (colorSelected) pink else lightGray,
                shape = RoundedCornerShape(8.dp)
            )
    )
}


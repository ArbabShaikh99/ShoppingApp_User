package com.example.shoppingapp_user.Presentation_layer.Screens.WishList

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shoppingapp_user.Presentation_layer.Navigation.ScreenNavigation.Routes
import com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components.SearchTextField
import com.example.shoppingapp_user.Presentation_layer.Screens.Home.Component.ProductCard
import com.example.shoppingapp_user.Presentation_layer.ViewModel.WishlistViewModel
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.CustomGray1
import com.example.shoppingapp_user.ui.theme.customGray
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray
import com.example.shoppingapp_user.ui.theme.pink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun WisListScreen (
    wishlistViewModel: WishlistViewModel = hiltViewModel(),
    navController: NavController
) {

    val coroutine = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutine.launch(Dispatchers.IO) {
            wishlistViewModel.getAllFavProduct()
        }
    }

    val getAllFavouiteProductsResponseState = wishlistViewModel.getAllFavProductState.collectAsState()
    val getAllFavouriteList = getAllFavouiteProductsResponseState.value.data
    val context = LocalContext.current

    var searchText by remember {
        mutableStateOf("")
    }

    if(getAllFavouiteProductsResponseState.value.isLoading){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = darkPink)
        }
    }
    else if (getAllFavouriteList.isNotEmpty()){
        val  FavProductList = if(searchText.isNotEmpty()){
            getAllFavouriteList.filter {
                it.name.contains(searchText , ignoreCase = true)
            }
        }
        else{
            getAllFavouriteList
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightGray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 85.dp, start = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                        Spacer(modifier = Modifier.width(11.dp))
                        Text(
                            text = "See Your Favourite",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 33.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SearchTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            "Search Product..",
                            heightSize = 50.dp,
                            widthSize = 337.dp,
                            TextFontSize = 17.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    HorizontalDivider(
                        thickness = 0.5.dp,
                        color = Color.Gray
                    )

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(FavProductList) {

                            FavouiteProductCard(
                                image = it.image,
                                price = it.finalprice,
                                productName = it.name,
                                onclick = {
                                    navController.navigate(Routes.EachProductId(productId = it.productId))
                                }
                            )
                    }
                }
            }
        }

}

                }
    else if (getAllFavouiteProductsResponseState.value.error.isNotEmpty()){
            Toast.makeText(context, getAllFavouiteProductsResponseState.value.error, Toast.LENGTH_SHORT).show()

    }
}















@Composable
fun FavouiteProductCard(
    productName :String,
    price : String,
    image:String,
    onclick :()-> Unit
){


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(lightGray)
            .padding(start = 18.dp ,  end = 18.dp, top = 20.dp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            ) {
                onclick()
            },
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Absolute.Left
    ) {
        Card(
            modifier = Modifier
                .height(120.dp)
                .width(80.dp)
                .background(lightGray),
            shape = RoundedCornerShape(8.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                error = painterResource(R.drawable.ic_launcher_background)
            )
        }
        Spacer(Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = productName,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W700,
                        textAlign = TextAlign.Start,
                        color = customGray,
                       // fontFamily = FontFamily(Font(R.font.montserrat_regular))
                    ),
                    modifier = Modifier.weight(1f), // Ensures wrapping without affecting Rs text
                    maxLines = if (productName.length > 8) 2 else 1, // Wrap only if >16 chars
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "Rs : ${price}",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W700,
                        textAlign = TextAlign.Start,
                        color = customGray,
                       // fontFamily = FontFamily(Font(R.font.montserrat_regular))
                    ),
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = "GF10125",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Start,
                    color = customGray,
                  //  fontFamily = FontFamily(Font(R.font.montserrat_regular))
                ),
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Size : UK10",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Start,
                    color = CustomGray1,
                   // fontFamily = FontFamily(Font(R.font.montserrat_regular))
                ),
            )
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Color :",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W700,
                        textAlign = TextAlign.Start,
                        color = customGray,
                       // fontFamily = FontFamily(Font(R.font.montserrat_regular))
                    ),
                )
                Spacer(Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .background(pink)
                )
            }
        }
    }
}
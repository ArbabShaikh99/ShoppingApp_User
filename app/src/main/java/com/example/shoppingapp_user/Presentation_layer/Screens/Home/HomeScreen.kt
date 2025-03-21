package com.example.shoppingapp_user.Presentation_layer.Screens.Home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.shoppingapp_user.Presentation_layer.Navigation.ScreenNavigation.Routes
import com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components.SearchTextField
import com.example.shoppingapp_user.Presentation_layer.Screens.Home.Component.Banner_PagerSlider
import com.example.shoppingapp_user.Presentation_layer.Screens.Home.Component.CategoryCard
import com.example.shoppingapp_user.Presentation_layer.Screens.Home.Component.ProductCard
import com.example.shoppingapp_user.Presentation_layer.ViewModel.HomeViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.ProductViewModel
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray
import com.example.shoppingapp_user.ui.theme.notificationColor
import com.example.shoppingapp_user.ui.theme.pink

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    ProductviewModel: ProductViewModel = hiltViewModel(),
    navController: NavController
) {
    val homeState by homeViewModel.homeScreenState.collectAsStateWithLifecycle()

    val searchState by ProductviewModel.searchProductState.collectAsState()
    val query by ProductviewModel.searchQuery.collectAsStateWithLifecycle()



    if (homeState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
           AnimatedLoading()
        }
    } else if (homeState.errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = homeState.errorMessage!!)
        }
    } else {

        Column(
            modifier = Modifier.fillMaxSize()
                .background(lightGray)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 45.dp, start = 23.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                SearchTextField(
                    value = query,
                    onValueChange = {newQuery ->
                        ProductviewModel.onSearchQueryChange(newQuery)
                    },
                    "Search..",
                    heightSize = 50.dp,
                    widthSize = 305.dp,
                    TextFontSize = 17.sp
                )

                IconButton(onClick = {
                    navController.navigate(Routes.NotificationScreenRoute)
                }) {
                    Icon(
                        Icons.Default.NotificationsNone, contentDescription = null,
                        tint = notificationColor, modifier = Modifier.size(30.dp)
                    )
                }
            }


            if (query.isNotEmpty() && searchState.data.isNotEmpty()) {
                Log.d("Search"," Data -- ${searchState.data}")

                Text(
                    text = buildAnnotatedString {
                        append("Showing results for ")
                        withStyle(style = SpanStyle(color = darkPink)) { // Define queryColor
                            append("\"$query\"")
                        }
                    },
                    color = Color.Black, // Default text color
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(16.dp)
                )

                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize()
                        .padding(top = 15.dp),
                    columns = GridCells.Fixed(3),
                ) {
                    items(searchState.data ?: emptyList()) {
                        ProductCard(
                            name = it.name,
                            image = it.image,
                            price = it.price,
                            finalPrice = it.finalprice,
                            productCode = it.createdBy
                        ) {
                            navController.navigate(Routes.EachProductId(productId = it.productId))
                        }
                    }
                }
            } else {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 23.dp, top = 14.dp, end = 23.dp),
                    horizontalArrangement = Arrangement.SpaceBetween // Ensures items are at opposite ends
                ) {
                    Text(
                        "Categories", color = notificationColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )

                    Text(
                        "See more", color = pink,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Routes.SeeAllCategoriesRoute)
                            }
                    )    // Right side
                }

                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(homeState.categories ?: emptyList()) {
                        CategoryCard(
                            ImageOfCategory = it.imageUri,
                            CategoryName = it.name,
                            onclick = {
                                navController.navigate(Routes.EachCategoryItemScreen(categoryName = it.name))
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Banner_PagerSlider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 23.dp, top = 0.dp, end = 23.dp),
                    horizontalArrangement = Arrangement.SpaceBetween // Ensures items are at opposite ends
                ) {
                    Text(
                        "Flash Sale", color = notificationColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Text(
                        "See more", color = pink,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Routes.SeeAllProductRoute)
                            }
                    )
                }

                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    // show All Product
                    items(homeState.products ?: emptyList()) {


                        ProductCard(
                            image = it.image,
                            name = it.name,
                            productCode = it.createdBy,
                            price = it.price,
                            finalPrice = it.finalprice,
                            onClick = {
                                navController.navigate(Routes.EachProductId(productId = it.productId))
                            }
                        )
                    }
                }

            }
        }


    }
    }


@Composable
fun AnimatedLoading() {

    val preloaderLottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever
    )


    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = Modifier
            .size(180.dp),
    )
}
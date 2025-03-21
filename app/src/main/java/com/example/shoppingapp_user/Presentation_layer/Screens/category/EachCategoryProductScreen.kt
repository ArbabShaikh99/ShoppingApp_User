package com.example.shoppingapp_user.Presentation_layer.Screens.category

import android.util.Log
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.shoppingapp_user.Presentation_layer.Screens.Home.Component.ProductCard
import com.example.shoppingapp_user.Presentation_layer.ViewModel.CategoryViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.ProductViewModel
import com.example.shoppingapp_user.R
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachCategoryProductScreen(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    productViewModel: ProductViewModel = hiltViewModel(),
    navController: NavController,
    categoryName :String
) {

    val getcategoryItemByProductState = categoryViewModel.getSpecificCategoryState.collectAsState()
    val getcategoryProductList = getcategoryItemByProductState.value.data

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val searchState by productViewModel.searchProductState.collectAsState()
    val query by productViewModel.searchQuery.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            categoryViewModel.getSpecificCategory(categoryName)
        }
    }

    when {
        getcategoryItemByProductState.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                AnimatedLoading()
            }
        }

        getcategoryItemByProductState.value.error.isNotBlank() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${getcategoryItemByProductState.value.error}")
            }
        }

        getcategoryProductList.isEmpty() -> {

            Box(
                modifier = Modifier.fillMaxSize()
                    .background(lightGray)
            ) {
                // Back Icon at the Top-Left
                Row(
                    modifier = Modifier.padding(
                        top = 40.dp, start = 18.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = darkPink,
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )

                    // Circle Shape (Top-Right Side)
                    Canvas(
                        modifier = Modifier
                            .size(190.dp)
                            .offset(x = 200.dp, y = -70.dp)
                    ) {
                        drawCircle(color = darkPink)
                    }
                }

                // Centered AnimatedEmpty
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedEmpty()
                }
            }


        }

        else -> {
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
                            .size(160.dp)
                            .offset(x = 250.dp, y = -60.dp)
                    ) {
                        drawCircle(color = darkPink)
                    }


                    // Column for all UI Elements
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                        // .background(lightGray)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 45.dp, start = 15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(25.dp)
                                    .clickable {
                                        navController.popBackStack()
                                    }
                            )
                            Spacer(modifier = Modifier.width(11.dp))
                            Text(
                                text = categoryName,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp, start = 33.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SearchTextField(
                                value = query,
                                onValueChange = { query ->
                                    productViewModel.onSearchQueryChange(query)
                                },
                                "Search Product",
                                heightSize = 50.dp,
                                widthSize = 325.dp,
                                TextFontSize = 14.sp
                            )

                            // on Search
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        if (query.isNotEmpty() && searchState.data.isNotEmpty()) {
                            Log.d("Search", " Data -- ${searchState.data}")


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

                            HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                modifier = Modifier.padding(
                                    top = 15.dp
                                ),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(getcategoryProductList) {
                                    ProductCard(
                                        name = it.name,
                                        image = it.image,
                                        price = it.price,
                                        finalPrice = it.finalprice,
                                        productCode = it.createdBy,
                                    ) {
                                        navController.navigate(Routes.EachProductId(productId = it.productId))
                                    }
                                }
                            }
                        }
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



@Composable
fun AnimatedEmpty() {
    val preloaderLottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.gaustempty))
    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = preloaderLottieComposition,
            progress = preloaderProgress,
            modifier = Modifier
                .size(230.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No Products Found",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}
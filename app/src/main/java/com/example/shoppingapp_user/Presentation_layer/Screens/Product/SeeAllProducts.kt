package com.example.shoppingapp_user.Presentation_layer.Screens.Product

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
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shoppingapp_user.Presentation_layer.Navigation.ScreenNavigation.Routes
import com.example.shoppingapp_user.Presentation_layer.Screens.Common_Components.SearchTextField
import com.example.shoppingapp_user.Presentation_layer.Screens.Home.Component.ProductCard
import com.example.shoppingapp_user.Presentation_layer.Screens.category.AnimatedLoading
import com.example.shoppingapp_user.Presentation_layer.ViewModel.ProductViewModel
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun SeeAllProductsScreen(
    productViewModel: ProductViewModel = hiltViewModel(),
    navController: NavController
) {
    val getAllProductState = productViewModel.getAllProductState.collectAsState()
    val getAllProductList = getAllProductState.value.data

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Fetch data when the screen loads
    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            productViewModel.getAllProduct()
        }
    }

    var searchProductName by remember { mutableStateOf("") }

    val listOfProduct = if (searchProductName.isNotEmpty()) {
        getAllProductList.filter {
            it.name.contains(searchProductName, ignoreCase = true)
        }
    } else {
        getAllProductList
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10000.dp) // Ensure Box has height
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
                            .size(30.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                    Spacer(modifier = Modifier.width(11.dp))
                    Text(
                        text = "See More",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 33.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchTextField(
                        value = searchProductName,
                        onValueChange = { searchProductName = it },
                        "Search Product..",
                        heightSize = 50.dp,
                        widthSize = 328.dp,
                        TextFontSize = 17.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = Color.Gray
                )

                // Handle Loading State
                when {
                    getAllProductState.value.isLoading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            AnimatedLoading()
                        }
                    }

                    getAllProductState.value.error.isNotBlank() -> {
                        // Handle error UI if needed
                    }

                    getAllProductState.value.data.isNotEmpty() -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            modifier = Modifier
                                .padding(top = 15.dp, end =15.dp )
                                .fillMaxWidth(),  // Ensure it takes full width
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(listOfProduct) {
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
}
    }



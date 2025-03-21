package com.example.shoppingapp_user.Presentation_layer.Screens.category

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.shoppingapp_user.Presentation_layer.Screens.Home.Component.CategoryCard
import com.example.shoppingapp_user.Presentation_layer.ViewModel.CategoryViewModel
import com.example.shoppingapp_user.ui.theme.darkPink
import com.example.shoppingapp_user.ui.theme.lightGray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch





@Composable
fun SeeAllCategoriesScreen(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    navController: NavController
) {
    val getAllCategoryState = categoryViewModel.getAllCategoryStat.collectAsState()
    val getAllcategoryList = getAllCategoryState.value.data

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            categoryViewModel.getAllCategory()
        }
    }

    var searchTextValue by remember { mutableStateOf("") }

    val filteredCategoryList = if (searchTextValue.isNotEmpty()) {
        getAllcategoryList.filter { it.name.contains(searchTextValue, ignoreCase = true) }
    } else {
        getAllcategoryList
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


            // Column for all UI Elements
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                // .background(lightGray)
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
                        value = searchTextValue,
                        onValueChange = { searchTextValue = it },
                        "Search category..",
                        heightSize = 53.dp,
                        widthSize = 328.dp,
                        TextFontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = Color.Gray
                )


                when {
                    getAllCategoryState.value.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            AnimatedLoading()
                        }
                    }

                    getAllCategoryState.value.error.isNotBlank() -> {
                        Toast.makeText(context, getAllCategoryState.value.error, Toast.LENGTH_SHORT)
                            .show()
                    }

                    filteredCategoryList.isNotEmpty() -> {


                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .fillMaxSize()
                            ) {

                                items(filteredCategoryList) { category ->
                                    CategoryCard(
                                        ImageOfCategory = category.imageUri,
                                        CategoryName = category.name,
                                        onclick = {
                                            navController.navigate(
                                                Routes.EachCategoryItemScreen(categoryName = category.name)
                                            )
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

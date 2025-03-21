package com.example.shoppingapp_user.Presentation_layer.Navigation.BottomNavigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bottombar.AnimatedBottomBar
import com.example.bottombar.components.BottomBarItem
import com.example.bottombar.model.IndicatorDirection
import com.example.bottombar.model.IndicatorStyle
import com.example.shoppingapp_user.Presentation_layer.Navigation.ScreenNavigation.AppNavigation
import com.example.shoppingapp_user.Presentation_layer.Navigation.ScreenNavigation.Routes
import com.example.shoppingapp_user.ui.theme.lightGray
import com.example.shoppingapp_user.ui.theme.pink
import com.google.firebase.auth.FirebaseAuth


@Composable
 fun  BottomNavigation(
    firebaseAuth: FirebaseAuth,
 ) {
    val navController = rememberNavController()


     var selectedItemIndex = remember{ mutableStateOf(0) }

    // Using this condition because not show BottomNavigation in signUp,login Screen
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val shouldShowBottomBar = remember { mutableStateOf(false) }

    LaunchedEffect(currentDestination) {
//        shouldShowBottomBar.value = when (currentDestination) {
//            Routes.loginScreenRoutes::class.qualifiedName,
//            Routes.SignUpScreenRoutes::class.qualifiedName,
//            Routes.EachProductId::class.simpleName -> false
//            else -> true
//        }
        shouldShowBottomBar.value = when {
//            currentDestination == Routes.loginScreenRoutes.toString() ||
                    currentDestination?.contains("SignUpScreenRoutes")==true ||
                     currentDestination?.contains("loginScreenRoutes")==true ||
                    currentDestination?.contains("EachProductId") == true ||
                    currentDestination?.contains("EachCategoryItemScreen") == true ||
                    currentDestination?.contains("SeeAllCategoriesRoute") == true ||
                    currentDestination?.contains("SeeAllProductRoute") == true ||
                    currentDestination?.contains("shippingRoute")  == true ||
                     currentDestination?.contains("PaymentRoute") == true   ||
                    currentDestination?.contains("NotificationScreenRoute") == true ||
                     currentDestination?.contains("SuccesslfullyPurchaseRoute") ==true  -> false
            else -> true
        }
    }
/////////////////////////////////////////////////////////////////////////
     val BottomitemsList = listOf(

         bottomNavigationitem(name = "Home", Icons.Default.Home, unseletedIcon = Icons.Outlined.Home),
         bottomNavigationitem(name = "WishList", Icons.Default.Favorite, unseletedIcon = Icons.Outlined.Favorite),
         bottomNavigationitem(name = "Cart", Icons.Default.ShoppingCart, unseletedIcon = Icons.Outlined.ShoppingCart),
         bottomNavigationitem(name = "Profile", Icons.Default.Person, unseletedIcon = Icons.Outlined.Person),

    )

    Scaffold (

        bottomBar = {
            if (shouldShowBottomBar.value) {

                Box(  // ✅ Shadow Effect ke liye Box use kar raha hoon
                    modifier = Modifier
                        .fillMaxWidth()
                        //.background(Color.White)
                        .shadow(15.dp)  // ✅ Shadow Effect
                ) {
                    AnimatedBottomBar(  // Handle Color,Size,
                        selectedItem = selectedItemIndex.value,
                        itemSize = BottomitemsList.size,
                        modifier = Modifier.padding(bottom = 27.dp),

                        containerColor = lightGray,
                        // indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        indicatorColor = pink,
                        // contentColor = lightGray,

                        indicatorDirection = IndicatorDirection.BOTTOM,


                        indicatorStyle = IndicatorStyle.FILLED


                    )

                    // NavigationBar() // ✅ Bottom Navigation Bar
                    {
                        BottomitemsList.forEachIndexed { index, bottomNavigationItems ->
                            //  NavigationBarItem
                            BottomBarItem(
                                selected = selectedItemIndex.value == index,  // Ye ensure karega ke jab item select ho, tab bhi text aur icon dono properly render hoon
                                onClick = {
                                    selectedItemIndex.value = index  // ✅ Select item (BUG: should be `=` not `==`)
                                    // navController.navigate(bottomNavigationItems.name)
                                    when (index) {
                                        0 -> navController.navigate(Routes.HomeRoutes)
                                        1 -> navController.navigate(Routes.WishPairListRoute)
                                        2 -> navController.navigate(Routes.CartScreenRoute)
                                        3 -> navController.navigate(Routes.ProfileScreenRoute)
                                    }
                                },

//                            icon = {
//                                Icon(
//                                    imageVector = if (selectedItemIndex == index) bottomNavigationItems.seletedIcon
//                                    else bottomNavigationItems.unseletedIcon, contentDescription = null) },

                                // ✅ Icon & Label Corrected Here
//                                imageVector = if (selectedItemIndex.value == index)
//                                    bottomNavigationItems.seletedIcon
//                                else bottomNavigationItems.unseletedIcon,
//
//                                label = bottomNavigationItems.name,
//
//                                iconColor = if (selectedItemIndex.value == index) pink else Color.Gray,
//                                textColor = if (selectedItemIndex.value == index) pink else Color.Gray
                       //     )
                                imageVector = bottomNavigationItems.seletedIcon,
                                label = bottomNavigationItems.name,
                                containerColor = Color.Transparent,
                            )
                        }
                    }
                }
            }
        }
    ){
        innerpadding ->
        Box(modifier = Modifier.fillMaxSize().padding(
            bottom = if(shouldShowBottomBar.value) innerpadding.calculateBottomPadding()  else 0.dp)){

            AppNavigation(firebaseAuth , navController)

        }
    }

}
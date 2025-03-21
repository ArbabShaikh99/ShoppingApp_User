package com.example.shoppingapp_user.Presentation_layer.Navigation.ScreenNavigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.shoppingapp_user.Presentation_layer.Screens.Auth.LoginScreen
import com.example.shoppingapp_user.Presentation_layer.Screens.Auth.SignUpScreen
import com.example.shoppingapp_user.Presentation_layer.Screens.Cart.CartScreenUI
import com.example.shoppingapp_user.Presentation_layer.Screens.Home.HomeScreen
import com.example.shoppingapp_user.Presentation_layer.Screens.Notification.NotificationScreen
import com.example.shoppingapp_user.Presentation_layer.Screens.Payment.Component.SuccessfullyPurchaseScreen
import com.example.shoppingapp_user.Presentation_layer.Screens.Payment.PaymentScreen
import com.example.shoppingapp_user.Presentation_layer.Screens.Product.AllDetailProductScreen
import com.example.shoppingapp_user.Presentation_layer.Screens.Product.SeeAllProductsScreen
import com.example.shoppingapp_user.Presentation_layer.Screens.Profile.ProfileScreenUI
import com.example.shoppingapp_user.Presentation_layer.Screens.Shiping.ShippingDetailsScreen
import com.example.shoppingapp_user.Presentation_layer.Screens.WishList.WisListScreen
import com.example.shoppingapp_user.Presentation_layer.Screens.category.EachCategoryProductScreen
import com.example.shoppingapp_user.Presentation_layer.Screens.category.SeeAllCategoriesScreen
import com.example.shoppingapp_user.Presentation_layer.ViewModel.CartViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.CategoryViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.HomeViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.OrderViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.ProductViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.ProfileViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.ShippingViewModel
import com.example.shoppingapp_user.Presentation_layer.ViewModel.WishlistViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(firebaseAuth :FirebaseAuth , navController: NavHostController) {

     val orderViewModel :OrderViewModel = hiltViewModel()
    val cartViewModel : CartViewModel = hiltViewModel()
    val wishListViewModel : WishlistViewModel = hiltViewModel()
    val homeViewModel : HomeViewModel = hiltViewModel()
    val categoryViewModel : CategoryViewModel = hiltViewModel()
    val productViewModel :ProductViewModel = hiltViewModel()
   val  shippingViewModel: ShippingViewModel = hiltViewModel()
  //  val  authviewModel: AuthViewModel = hiltViewModel()
    val profileViewModel : ProfileViewModel = hiltViewModel()


    var startScreen = if(firebaseAuth.currentUser ==
        null){
        SubNavigation.LoginSignRoutes
    }
    else{
        SubNavigation.MainHomeRoutes
    }

      NavHost(navController = navController , startDestination = startScreen ) {


          navigation<SubNavigation.LoginSignRoutes>(startDestination = Routes.loginScreenRoutes){

              composable<Routes.loginScreenRoutes> {
                  LoginScreen(navController = navController)
              }
              composable<Routes.SignUpScreenRoutes> {
                  SignUpScreen(navController =navController)
              }
          }


          navigation<SubNavigation.MainHomeRoutes>(startDestination = Routes.HomeRoutes){

              composable<Routes.HomeRoutes> {
                //  HomeScreen(navController = navController)
                  HomeScreen(homeViewModel,productViewModel , navController)
              }

              composable<Routes.EachProductId> {
                  val product : Routes.EachProductId =  it.toRoute()
                  AllDetailProductScreen(
                       productViewModel,
                      wishListViewModel,
                     cartViewModel,
                      navController,
                      productID = product.productId
                  )
              }
              composable<Routes.EachCategoryItemScreen> {
                  val category : Routes.EachCategoryItemScreen = it.toRoute()
                  EachCategoryProductScreen(
                      categoryViewModel,
                      productViewModel,
                      navController,
                      categoryName = category.categoryName
                  )
              }
              composable<Routes.WishPairListRoute> {
                  WisListScreen(
                      wishListViewModel,
                      navController
                  )
              }
              composable<Routes.CartScreenRoute> {
                  CartScreenUI(
                      cartViewModel,
                      navController
                  )
              }
              composable<Routes.ProfileScreenRoute> {
                  ProfileScreenUI(profileViewModel , firebaseAuth , navController)
              }

              composable<Routes.SeeAllCategoriesRoute> {
                  SeeAllCategoriesScreen(categoryViewModel ,navController)
              }
              composable<Routes.SeeAllProductRoute> {
                  SeeAllProductsScreen(productViewModel , navController)
              }

              composable<Routes.shippingRoute> {
                  val productId = it.toRoute<Routes.shippingRoute>().productId
                  val productQty  = it.toRoute<Routes.shippingRoute>().productQty
                  val color = it.toRoute<Routes.shippingRoute>().color
                  val size = it.toRoute<Routes.shippingRoute>().size

                  ShippingDetailsScreen(
                      cartViewModel,
                      productViewModel,
                      shippingViewModel,
                      navController,
                      productID = productId,
                      productQty = productQty,
                      color = color,
                      size = size
                  )
              }
              composable<Routes.PaymentRoute> {
                  PaymentScreen(
                      cartViewModel,
                      orderViewModel,
                      shippingViewModel,
                      navController
                  )
              }

              composable<Routes.SuccesslfullyPurchaseRoute>{
                  SuccessfullyPurchaseScreen(navController)
              }
              composable<Routes.NotificationScreenRoute> {
                  NotificationScreen(navController)
              }

          }




     }
}



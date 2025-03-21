package com.example.shoppingapp_user.Presentation_layer.Navigation.ScreenNavigation

import kotlinx.serialization.Serializable

sealed class SubNavigation{
    @Serializable
    object MainHomeRoutes : SubNavigation()

    @Serializable
    object LoginSignRoutes : SubNavigation()
}

sealed class Routes {
    @Serializable
    object HomeRoutes

    @Serializable
    object loginScreenRoutes

    @Serializable
    object SignUpScreenRoutes

    @Serializable
    data class EachProductId(
     val productId :String
 )


    @Serializable
    object WishPairListRoute

    @Serializable
    data class shippingRoute(
        val productId :String = "",
        val productQty :String= "",
        val color :String= "",
        val size :String= ""
    )



    @Serializable
    object CartScreenRoute

    @Serializable
    data class EachCategoryItemScreen(
      val   categoryName :String
    )

@Serializable
object ProfileScreenRoute

    @Serializable
    object SeeAllCategoriesRoute

    @Serializable
    object SeeAllProductRoute

    @Serializable
    object PaymentRoute

    @Serializable
   object SuccesslfullyPurchaseRoute

    @Serializable
    object NotificationScreenRoute


}
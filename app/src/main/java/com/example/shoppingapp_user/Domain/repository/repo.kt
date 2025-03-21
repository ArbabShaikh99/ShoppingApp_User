package com.example.shoppingapp_user.Domain.repository

import android.net.Uri
import com.example.shoppingapp_user.Common.ResultState
import com.example.shoppingapp_user.Domain.Models.BannerModel
import com.example.shoppingapp_user.Domain.Models.CartModel
import com.example.shoppingapp_user.Domain.Models.OrderModel
import com.example.shoppingapp_user.Domain.Models.ShippingModel
import com.example.shoppingapp_user.Domain.Models.UserDataModel
import com.example.shoppingapp_user.Domain.Models.categoryModel
import com.example.shoppingapp_user.Domain.Models.getAllProductsModel
import kotlinx.coroutines.flow.Flow

interface repo {




    // Auth
    fun  registerUserWithEmailAndPassword(userItems : UserDataModel) : Flow<ResultState<String>>
    fun  loginUserWithEmailAndPassword(userItems :UserDataModel): Flow<ResultState<String>>

    //User
    fun getUserData(UserId :String):Flow<ResultState<UserDataModel>>
    fun InsertUserImage(userimageUri : Uri) :Flow<ResultState<String>>
    fun UpdateeUserData(userData: UserDataModel) :Flow<ResultState<String>>
    fun UpdateUserImage(imageUri :Uri): Flow<ResultState<String>>



    // Product
  fun getAllProducts():Flow<ResultState<List<getAllProductsModel>>>
  fun getProductByID(productId:String): Flow<ResultState<getAllProductsModel>>

    // category
    fun   getAllCategory(): Flow<ResultState<List<categoryModel>>>
    fun  getSpecificCategory(categoryName :String):Flow<ResultState<List<getAllProductsModel>>>

    //SearchProduct
    fun searchProduct(query:String): Flow<ResultState<List<getAllProductsModel>>>

    // Home
    fun   getCategoryInLimit(): Flow<ResultState<List<categoryModel>>>
    fun getProductInLimit():Flow<ResultState<List<getAllProductsModel>>>

    // Banner
   // fun getBanner():Flow<ResultState<List<BannerModel>>>


    // Favouite Product WishList
    fun FavouiteProduct(productModel : getAllProductsModel): Flow<ResultState<String>>
    fun FavouiteProductORNot(ProductID :String): Flow<ResultState<Boolean>>
     fun getAllFavouiteProduct(): Flow<ResultState<List<getAllProductsModel>>>

     // User Cart Products
     fun CardProduct(cartModel :CartModel) : Flow<ResultState<String>>
     fun ProductInCartorNot(ProductId:String): Flow<ResultState<Boolean>>
     fun getAllProductInCart(): Flow<ResultState<List<CartModel>>>







     // Shipping User Data for delivery
     fun ShippingDataSave(shippingModel :ShippingModel) : Flow<ResultState<String>>
    fun shippingDataGetThroughUID() : Flow<ResultState<ShippingModel>>

    // Order
    fun OrderSave(orderModel : List<OrderModel>):Flow<ResultState<String>>
    fun getAllOrder():Flow<ResultState<List<OrderModel>>>
}
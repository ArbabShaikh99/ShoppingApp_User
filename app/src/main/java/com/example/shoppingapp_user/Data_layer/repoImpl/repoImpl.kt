package com.example.shoppingapp_user.Data_layer.repoImpl

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.shoppingapp_user.Common.All_Cart
import com.example.shoppingapp_user.Common.CATEGORY
import com.example.shoppingapp_user.Common.CONSTANT_PRODUCT
import com.example.shoppingapp_user.Common.FAVOURITE
import com.example.shoppingapp_user.Common.ORDER
import com.example.shoppingapp_user.Common.ORDER_DATA
import com.example.shoppingapp_user.Common.ResultState
import com.example.shoppingapp_user.Common.SHIPPING_DATA
import com.example.shoppingapp_user.Common.SUPABASE_URL
import com.example.shoppingapp_user.Common.USERS
import com.example.shoppingapp_user.Common.USER_FAVOUITE
import com.example.shoppingapp_user.Common.USER_TOKEN
import com.example.shoppingapp_user.Common.User_CartDetails
import com.example.shoppingapp_user.Domain.Models.CartModel
import com.example.shoppingapp_user.Domain.Models.OrderModel
import com.example.shoppingapp_user.Domain.Models.ShippingModel
import com.example.shoppingapp_user.Domain.Models.UserDataModel
import com.example.shoppingapp_user.Domain.Models.categoryModel
import com.example.shoppingapp_user.Domain.Models.getAllProductsModel
import com.example.shoppingapp_user.Domain.repository.repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.InputStream
import javax.inject.Inject


class repoImpl @Inject constructor(
    private  val firebase :FirebaseFirestore,
    private val firebasAuth : FirebaseAuth,
    private val firebaseStorage: FirebaseStorage,
    private val supabaseStorage : Storage,
    private val context :Context
   /// private val firebaseMessage : FirebaseMessaging,
  //  private val pushNotification : PushNotification
    ) : repo {


    ///                 Auth                     //
    override fun registerUserWithEmailAndPassword(userItems: UserDataModel): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebasAuth.createUserWithEmailAndPassword(userItems.email, userItems.password)
                .addOnSuccessListener {
                    firebase.collection(USERS).document(it.user?.uid.toString()).set(userItems)
                        .addOnSuccessListener {
                            trySend(ResultState.Success("User Register Successfully"))
                            // Token for Using Notificaion
                            // updateFCM_Token(firebasAuth.currentUser?.uid.toString())
                        }

                        .addOnFailureListener {
                            trySend(ResultState.Error(it.toString()))
                        }
                }
                .addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun loginUserWithEmailAndPassword(userItems: UserDataModel):
            Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        firebasAuth.signInWithEmailAndPassword(userItems.email, userItems.password)
            .addOnSuccessListener {
                trySend(ResultState.Success(" login  Successfully"))
                // Token for Using Notificaion
                // updateFCM_Token(firebasAuth.currentUser?.uid.toString())
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        awaitClose {
            close()
        }
    }


    ///                 User/Profile                     //
    override fun getUserData(UserId: String): Flow<ResultState<UserDataModel>> = callbackFlow {

        trySend(ResultState.Loading)

        firebase.collection(USERS).document(UserId).get()
            .addOnSuccessListener {
                val UserId = it.toObject(UserDataModel::class.java).apply {
                    this?.UserID = it.id
                }
                trySend(ResultState.Success(UserId!!))
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        awaitClose {
            close()
        }
    }

    //    override fun InsertUserImage(userimageUri: Uri): Flow<ResultState<String>> = callbackFlow {
//        trySend(ResultState.Loading)
//
//        firebaseStorage.reference.child("UserProfileImage${System.currentTimeMillis()}")
//            .putFile(userimageUri)
//            .addOnSuccessListener {
//                it.storage.downloadUrl.addOnSuccessListener {
//                    trySend(ResultState.Success(it.toString()))
//                }
//                if(it.error !=null ){
//                    trySend(ResultState.Error(it.error.toString()))
//                }
//            }
//        awaitClose {
//            close()
//        }
//
//    }
    override fun InsertUserImage(userimageUri: Uri): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        var inputStream: InputStream? = null
        try {
            // Open the input stream from the URI
            inputStream = context.contentResolver.openInputStream(userimageUri)
                ?: throw Exception("File not found")
            val fileBytes = inputStream.readBytes()
            val fileName = "UserImages/${System.currentTimeMillis()}.jpg"

            // Upload the file to Supabase Storage
            supabaseStorage.from("ProductImages").upload(fileName, fileBytes)

            // If no exception is thrown, the upload is successful
            val fileUrl = "${SUPABASE_URL}/storage/v1/object/public/ProductImages/$fileName"
            trySend(ResultState.Success(fileUrl))
            Log.e("InsertImage", "Succesfully uploading image: ${ResultState.Success(fileUrl)}")
        } catch (e: Exception) {
            // Log the exception for debugging
            Log.e("InsertImage", "Error uploading image: ${e.message}", e)

            // Send a detailed error message
            trySend(ResultState.Error(e.message ?: "Upload failed"))
        } finally {
            // Close the input stream
            inputStream?.close()
        }

        awaitClose { close() }
    }


    override fun UpdateeUserData(userData: UserDataModel): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebase.collection(USERS).document(firebasAuth.uid.toString()).set(userData)
                .addOnSuccessListener {
                    trySend(ResultState.Success("User Data Update Successfully"))
                }
                .addOnFailureListener {
                    trySend(ResultState.Error(it.message.toString()))
                }
            awaitClose {
                close()
            }

        }

//    override fun UpdateUserImage(imageUri: Uri): Flow<ResultState<String>> = callbackFlow {

//        firebaseStorage.reference.child(
//            "UserProfileImage/${
//                System
//                    .currentTimeMillis()
//            }+${firebasAuth.currentUser?.uid}"
//        ).putFile(imageUri)
//            .addOnSuccessListener {
//                it.storage.downloadUrl.addOnSuccessListener { image ->
//                    trySend(ResultState.Success(image.toString()))
//                }
//            }
//            .addOnFailureListener {
//                trySend(ResultState.Error(it.toString()))
//            }
//        awaitClose { close() }
//    }
override fun UpdateUserImage(imageUri: Uri): Flow<ResultState<String>> = callbackFlow {
    trySend(ResultState.Loading)

    var inputStream: InputStream? = null
    try
    {
        // Open the input stream from the URI
        inputStream = context.contentResolver.openInputStream(imageUri) ?: throw Exception("File not found")
        val fileBytes = inputStream.readBytes()
        val fileName = "UserImages/${System.currentTimeMillis()}.jpg"

        // Upload the file to Supabase Storage
        supabaseStorage.from("ProductImages").upload(fileName, fileBytes)

        // If no exception is thrown, the upload is successful
        val fileUrl = "${SUPABASE_URL}/storage/v1/object/public/ProductImages/$fileName"
        trySend(ResultState.Success(fileUrl))
        Log.e("InsertImage", "Succesfully uploading image: ${ResultState.Success(fileUrl)}")
    } catch (e: Exception)
    {
        // Log the exception for debugging
        Log.e("InsertImage", "Error uploading image: ${e.message}", e)

        // Send a detailed error message
        trySend(ResultState.Error(e.message ?: "Upload failed"))
    } finally
    {
        // Close the input stream
        inputStream?.close()
    }

    awaitClose{ close() }
}



    ///                 Product                     //
    override fun getAllProducts(): Flow<ResultState<List<getAllProductsModel>>> = callbackFlow{

        trySend( ResultState.Loading)

        firebase.collection(CONSTANT_PRODUCT).get()
            .addOnSuccessListener {
                val AllProducts = it.documents.mapNotNull {
                    it.toObject(getAllProductsModel::class.java)?.apply {
                        productId = it.id
                    }
                }
                trySend(ResultState.Success(AllProducts))
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }
        awaitClose{
            close()
        }

    }

    override fun getProductByID(productId: String): Flow<ResultState<getAllProductsModel>> = callbackFlow {
       trySend(ResultState.Loading)

        firebase.collection(CONSTANT_PRODUCT).document(productId).get()
            .addOnSuccessListener {
             val product = it.toObject(getAllProductsModel::class.java)
                trySend(ResultState.Success(product!!))
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }
        awaitClose {
            close()
        } }





          ///                 Category                     //
    override fun getAllCategory(): Flow<ResultState<List<categoryModel>>> =  callbackFlow{
        trySend(ResultState.Loading)

        firebase.collection(CATEGORY).get()
            .addOnSuccessListener {

                // Check data not null
                val AllCategory = it.documents.mapNotNull {
                    it.toObject(categoryModel::class.java)
                }

                trySend(ResultState.Success(AllCategory))
            }
            .addOnFailureListener{
                trySend(ResultState.Error(it.toString()))
            }
        awaitClose{
            close()
        }
    }

    override fun getSpecificCategory(categoryName: String): Flow<ResultState<List<getAllProductsModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        firebase.collection(CONSTANT_PRODUCT).whereEqualTo("category",categoryName).get()
            .addOnSuccessListener {
                 val product = it.documents.mapNotNull {
                     it.toObject(getAllProductsModel::class.java)?.apply {
                         productId = it.id
                     }
                 }
                trySend(ResultState.Success(product))
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }
        awaitClose {
            close()
        }

    }

//                   Search Product              ///
    override fun searchProduct(query: String): Flow<ResultState<List<getAllProductsModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        firebase.collection(CONSTANT_PRODUCT)
            .get()
            .addOnSuccessListener { result ->
                val products = result.documents.mapNotNull {
                    it.toObject(getAllProductsModel::class.java)?.apply {
                        productId = it.id
                    }
                }

                // 🔹 Case-Insensitive Filtering in Kotlin (Firestore doesn't support lowercase search)
                val filteredProducts = products.filter {
                    it.name.contains(query, ignoreCase = true)
                }

                trySend(ResultState.Success(filteredProducts))
            }
            .addOnFailureListener { e ->
                trySend(ResultState.Error(e.toString()))
            }
        awaitClose {
            close()
        }
    }




    //         For Home Screen Limit Data
    override fun getCategoryInLimit(): Flow<ResultState<List<categoryModel>>> = callbackFlow {

             trySend(ResultState.Loading)

             firebase.collection(CATEGORY).limit(5).get()
                 .addOnSuccessListener {

                     // Check data not null
                     val AllCategory = it.documents.mapNotNull {
                         it.toObject(categoryModel::class.java)
                     }

                     trySend(ResultState.Success(AllCategory))
                 }
                 .addOnFailureListener{
                     trySend(ResultState.Error(it.toString()))
                 }
             awaitClose{
                 close()
             }

    }

    override fun getProductInLimit(): Flow<ResultState<List<getAllProductsModel>>> = callbackFlow{

        trySend( ResultState.Loading)

        firebase.collection(CONSTANT_PRODUCT).limit(5).get()
            .addOnSuccessListener {
                val AllProducts = it.documents.mapNotNull {
                    it.toObject(getAllProductsModel::class.java)?.apply {
                        productId = it.id
                    }
                }
                trySend(ResultState.Success(AllProducts))
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }
        awaitClose{
            close()
        }

    }


    //            Favouite Product WishList            //

    override fun FavouiteProduct(productModel: getAllProductsModel): Flow<ResultState<String>> = callbackFlow{
      trySend(ResultState.Loading)

        firebase.collection(FAVOURITE).document(firebasAuth.currentUser?.uid.toString()).collection(USER_FAVOUITE)
            .whereEqualTo("productId", productModel.productId).get()
            .addOnSuccessListener {

                if(it.documents.isNotEmpty()){
                    firebase.collection(FAVOURITE).document(firebasAuth.currentUser?.uid.toString()).collection(USER_FAVOUITE)
                        .document(it.documents[0].id)
                        .delete()
                        .addOnSuccessListener {
                            trySend(ResultState.Success("Product Removed From Favourite"))
                                close()
                        }
                        .addOnFailureListener {
                            trySend(ResultState.Error(it.toString()))
                            close()
                        }
                    return@addOnSuccessListener
                }
                else{
                    firebase.collection(FAVOURITE).document(firebasAuth.currentUser?.uid.toString()).collection(
                        USER_FAVOUITE).document()
                        .set(productModel)
                        .addOnSuccessListener {
                            trySend(ResultState.Success("Product Add Successfully"))
                            close()
                        }
                        .addOnFailureListener {
                            trySend(ResultState.Error(it.toString()))
                            close()
                        }
                }
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
                return@addOnFailureListener
            }
        awaitClose{
            close()
        }
    }

    override  fun FavouiteProductORNot(ProductID :String): Flow<ResultState<Boolean>> = callbackFlow {
        trySend(ResultState.Loading)

        firebase.collection(FAVOURITE).document(firebasAuth.currentUser?.uid.toString()).collection(USER_FAVOUITE)
            .whereEqualTo("productId" , ProductID).get()
            .addOnSuccessListener {
               if( it.documents.isNotEmpty()){
                   trySend(ResultState.Success(true))
               }
                else{
                    trySend(ResultState.Success(false))
                }
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        awaitClose {
            close()
        }
    }

    override fun getAllFavouiteProduct(): Flow<ResultState<List<getAllProductsModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        firebase.collection(FAVOURITE).document(firebasAuth.currentUser?.uid.toString()).collection(USER_FAVOUITE)
            .get()
            .addOnSuccessListener {
              val productData = it.documents.mapNotNull {
                    it.toObject(getAllProductsModel::class.java)
                }
                trySend(ResultState.Success(productData))
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        awaitClose {
            close()
        }
    }

    //                  Cart Product                      ///

    override fun CardProduct(cartModel: CartModel): Flow<ResultState<String>> = callbackFlow{
      trySend(ResultState.Loading)

        firebase.collection(All_Cart).document(firebasAuth.currentUser?.uid.toString()).collection(User_CartDetails)
            .whereEqualTo("productId" , cartModel.productId).get()
            .addOnSuccessListener {
                if(it.documents.isNotEmpty()){
                    firebase.collection(All_Cart).document(firebasAuth.currentUser?.uid.toString()).collection(
                        User_CartDetails).document(it.documents[0].id)
                        .delete()
                        .addOnSuccessListener {
                            trySend(ResultState.Success("Product Removed From Cart"))
                            close()
                        }
                        .addOnFailureListener{
                            trySend(ResultState.Error(it.message.toString()))
                            close()
                        }
                    return@addOnSuccessListener
                }
                else {
                    firebase.collection(All_Cart).document(firebasAuth.currentUser?.uid.toString())
                        .collection(
                            User_CartDetails
                        ).document().set(cartModel)
                        .addOnSuccessListener {
                            trySend(ResultState.Success("Product Added In Cart"))
                            close()
                        }
                        .addOnFailureListener {
                            trySend(ResultState.Error(it.message.toString()))
                            close()
                        }
                }

            }.addOnFailureListener{
                     trySend(ResultState.Error(it.message.toString()))
                        return@addOnFailureListener
                    }
                awaitClose {
                    close()
                }
    }



    override fun ProductInCartorNot(ProductId: String): Flow<ResultState<Boolean>> =  callbackFlow {
       trySend(ResultState.Loading)

        firebase.collection(All_Cart).document(firebasAuth.currentUser?.uid.toString()).collection(
            User_CartDetails).whereEqualTo("ProductId" , ProductId).get()
            .addOnSuccessListener {
                if(it.documents.isNotEmpty()){
                    trySend(ResultState.Success(true))
                }
                else{
                    trySend(ResultState.Success(false))
                }
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        awaitClose {
            close()
        }
    }

    override fun getAllProductInCart(): Flow<ResultState<List<CartModel>>>  =  callbackFlow{
        trySend(ResultState.Loading)

        firebase.collection(All_Cart).document(firebasAuth.currentUser?.uid.toString()).collection(
            User_CartDetails).get()
            .addOnSuccessListener {
                val cartdetails = it.documents.mapNotNull {
                    it.toObject(CartModel::class.java)
                }
                trySend(ResultState.Success(cartdetails))
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        awaitClose {
            close()
        }
    }

    override fun ShippingDataSave(shippingModel: ShippingModel): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebase.collection(SHIPPING_DATA)
                .document(firebasAuth.currentUser?.uid.toString()).set(shippingModel)
                .addOnSuccessListener {
                    trySend(ResultState.Success("Shipping Data Saved Successfully"))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.message.toString())) }
            awaitClose { close() }
    }

    override fun shippingDataGetThroughUID(): Flow<ResultState<ShippingModel>> = callbackFlow {
        trySend(ResultState.Loading)
        firebase.collection(SHIPPING_DATA).document(firebasAuth.currentUser?.uid!!).get()
            .addOnSuccessListener {
                val shippingData = it.toObject(ShippingModel::class.java)
                Log.d("@payment", "shippingDataGetThroughUID: ${shippingData?.saveForNextTime}")
                trySend(ResultState.Success(shippingData.let {
                    shippingData ?: ShippingModel()
                }))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        awaitClose {
            close() } }

    override fun OrderSave(orderModel: List<OrderModel>): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        val orderMap = orderModel.mapIndexed{ index, order -> index.toString() to order }.toMap()

        firebase.collection(ORDER_DATA)
            .document(firebasAuth.currentUser?.uid.toString()).collection(ORDER).document()
            .set(orderMap)
            .addOnSuccessListener {
                trySend(ResultState.Success("Order Successfully"))
//                pushNotification.sendNotification(
//                title = "Order Initiate check",
//                    message = "Order Successfully"
//                )
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        awaitClose {
            close()
        }
    }

    override fun getAllOrder(): Flow<ResultState<List<OrderModel>>>  = callbackFlow {
        trySend(ResultState.Loading)

        firebase.collection(ORDER_DATA).document(firebasAuth.currentUser?.uid.toString())
            .collection(ORDER).get().addOnSuccessListener { querySnapshot ->
                val allOrders = mutableListOf<OrderModel>()

                querySnapshot.documents.forEach { document ->
                    // Iterate over all fields in the document
                    val maps = document.data // Get all the fields as a Map<String, Any>
                    maps?.forEach { (_, value) ->
                        val orderMap = value as? Map<*, *> // Cast each value to Map
                        orderMap?.let {
                            val order =  OrderModel(
                                city = it["city"] as? String ?: "",
                                color = it["color"] as? String ?: "",
                                countryRegion = it["countryRegion"] as? String ?: "",
                                date = it["date"] as? Long ?: 0,
                                firstName = it["firstName"] as? String ?: "",
                                lastName = it["lastName"] as? String ?: "",
                                mobileNo = it["mobileNo"] as? String ?: "",
                                postalCode = it["postalCode"] as? String ?: "",
                                productCategory = it["productCategory"] as? String ?: "",
                                productId = it["productId"] as? String ?: "",
                                productDescription = it["productDescription"] as? String ?: "",
                                productFinalPrice = it["productFinalPrice"] as? String ?: "",
                                productImageUrl = it["productImageUrl"] as? String ?: "",
                                productPrice = it["productPrice"] as? String ?: "",
                                productName = it["productName"] as? String ?: "",
                                productQty = it["productQty"] as? String ?: "",
                                size = it["size"] as? String ?: "",
                                totalPrice = it["totalPrice"] as? String ?: "",
                                transactionId = it["transactionId"] as? String ?: "",
                                transactionMethod = it["transactionMethod"] as? String ?: "",
                                userAddress = it["userAddress"] as? String ?: "",
                                userEmail = it["userEmail"] as? String ?: "",
                                orderId = it["orderId"] as? String ?: "",
                            )
                            allOrders.add(order)
                        }
                    }
                }
                if (allOrders.isEmpty()) {
                    trySend(ResultState.Error("No orders found"))
                } else {
                    trySend(ResultState.Success(allOrders))
                }

                Log.d("@order", "getAllOrderData: ${allOrders.get(0).productName}")
            }.addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        awaitClose {
            close()
        }
    }


//    private fun updateFCM_Token(userID:String){
//        firebaseMessage.token.addOnCompleteListener {
//            if (it.isSuccessful){
//                val token  = it.result
//                firebase.collection(USER_TOKEN).document(userID).set(mapOf("token" to token))
//            }
//        }
//    }

}
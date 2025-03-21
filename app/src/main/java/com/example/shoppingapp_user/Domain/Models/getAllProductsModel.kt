package com.example.shoppingapp_user.Domain.Models

data class getAllProductsModel(
    val name:String = "",
    val description:String = "",
    val price:String = "",
    val finalprice:String = "",
    val category:String = "",
    val image:String = "",
    val createdBy:String = "",
    val date : Long =System.currentTimeMillis(),
    val availableUnits : Long = 0,
    var productId : String = ""
)
// This For Cart Section
fun getAllProductsModel.toCartModel(
    productQty : String = "1",// Default quantity
    color : String = "Default Color", // Default color
    size: String = "Default Size" // Default size
) : CartModel{
    return CartModel(
        productName = this.name,
        productId = this.productId,
        productDescription = this.description,
        productPrice = this.price,
        productFinalPrice = this.finalprice,
        productImageUrl = this.image,
        productCategory = this.category,
        productQty = productQty,
        color = color,
        size = size
    )
}
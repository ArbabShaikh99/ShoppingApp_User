package com.example.shoppingapp_user.Presentation_layer.Screens.Cart.component

import com.example.shoppingapp_user.Domain.Models.CartModel

fun calculatePercentage(
    originalPrice: Double,
    discountedPrice: Double
): Int {
    val percentageDiscount = ((originalPrice - discountedPrice) / originalPrice) * 100

    return percentageDiscount.toInt()
}

fun sumAllTotalPrice(cartModel: List<CartModel>): Double {
    var totalPrice = 0.0
    for (item in cartModel) {
        totalPrice += item.productFinalPrice.toInt()*item.productQty.toInt()
    }
    return totalPrice
}
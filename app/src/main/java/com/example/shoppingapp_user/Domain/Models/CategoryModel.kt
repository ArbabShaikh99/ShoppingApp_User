package com.example.shoppingapp_user.Domain.Models

data class categoryModel(
    var name :String = "",
    val description :String ="",
    val date : Long =System.currentTimeMillis(),
    var imageUri : String = ""
)

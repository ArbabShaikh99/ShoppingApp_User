package com.example.shoppingapp_user.Domain.Models

data class UserDataModel(
    var firstName :String = "",
    val lastName :String = "",
    val email :String = "",
    val password :String = "",
    val confirmPassword: String = "",
    val phoneNumber :String = "",
    val address :String = "",
    val userImage :String = "",
    var UserID :String =""
)

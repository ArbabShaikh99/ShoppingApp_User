package com.example.shoppingapp_user.Domain.Models

data class BannerModel(
    val bannerName: String ="",
    val bannerImage:String ="",
    val bannerAddedDate: Long = System.currentTimeMillis()
)

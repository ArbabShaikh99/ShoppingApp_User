package com.example.shoppingapp_user.Domain.Models

 data class ShippingModel (
     val email : String = "",
     val mobileNo : String = "",
     val countryRegion : String = "",
     val firstName : String = "",
     val lastName : String = "",
     val address : String = "",
     val city : String = "",
     val postalCode : String = "",
     val saveForNextTime : Boolean = false
 )
package com.example.shoppingapp_user.Common

sealed  class ResultState<out T>{
    data class Success<T>(val data:T):ResultState<T>()
    data class Error(val error:String):ResultState<Nothing>()
    object Loading :ResultState<Nothing>()
}
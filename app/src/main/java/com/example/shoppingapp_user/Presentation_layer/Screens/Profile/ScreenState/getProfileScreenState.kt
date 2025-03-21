package com.example.shoppingapp_user.Presentation_layer.Screens.Profile.ScreenState

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


data class getProfileScreenState(
    val firstname : MutableState<String> = mutableStateOf(""),
    val lastname : MutableState<String> = mutableStateOf(""),
    val email : MutableState<String> = mutableStateOf(""),
    val password : MutableState<String> = mutableStateOf(""),
    val confirmpassword: MutableState<String> = mutableStateOf(""),
    val phoneNumber : MutableState<String> = mutableStateOf(""),
    val address : MutableState<String> = mutableStateOf(""),
    val userimageUrl : MutableState<String> = mutableStateOf(""),
    val userimageUri : MutableState<Uri?> = mutableStateOf(null)
)

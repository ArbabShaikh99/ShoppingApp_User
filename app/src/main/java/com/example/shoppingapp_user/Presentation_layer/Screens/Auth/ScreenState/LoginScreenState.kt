package com.example.shoppingapp_user.Presentation_layer.Screens.Auth.ScreenState

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class LoginScreenState(
    val email : MutableState<String> = mutableStateOf(""),
    val password : MutableState<String> = mutableStateOf(""),
)

package com.example.shoppingapp_user.Domain.UseCase.User

import android.net.Uri
import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class InsertProfileImageUseCase @Inject constructor(
    private val repo : repo
) {
    fun insertProfileImageUseCase(imageUri :Uri)= repo.InsertUserImage(imageUri)
}
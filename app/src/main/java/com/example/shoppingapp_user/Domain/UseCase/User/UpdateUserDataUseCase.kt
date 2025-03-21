package com.example.shoppingapp_user.Domain.UseCase.User

import com.example.shoppingapp_user.Domain.Models.UserDataModel
import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class UpdateUserDataUseCase @Inject constructor(
    private val repo : repo
) {
    fun updateUserDataUseCase(userData:UserDataModel) = repo.UpdateeUserData(userData)
}
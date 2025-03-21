package com.example.shoppingapp_user.Domain.UseCase.Auth

import com.example.shoppingapp_user.Domain.Models.UserDataModel
import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class loginUserWithEmailAndPasswordUseCase @Inject constructor(
    private val repo :repo
) {
    suspend fun loginUserWithEmailAndPasswordUseCase(
        userItems : UserDataModel)=
            repo.loginUserWithEmailAndPassword(userItems)
}
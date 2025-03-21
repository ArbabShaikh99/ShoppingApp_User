package com.example.shoppingapp_user.Domain.UseCase.Auth

import com.example.shoppingapp_user.Domain.Models.UserDataModel
import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class RegisterUserWithEmailAndPasswordUseCase @Inject
constructor(private val repo : repo) {

    suspend fun registerUserWithEmailAndPasswordUseCase(UserItems : UserDataModel) =
        repo.registerUserWithEmailAndPassword(UserItems)
}
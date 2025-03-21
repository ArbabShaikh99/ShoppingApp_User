package com.example.shoppingapp_user.Domain.UseCase.User

import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class GetUserProfileDataUseCase @Inject constructor(
    private val repo :repo
) {
    fun getUserProfileDataUseCase(UserID:String) = repo.getUserData(UserID)
}
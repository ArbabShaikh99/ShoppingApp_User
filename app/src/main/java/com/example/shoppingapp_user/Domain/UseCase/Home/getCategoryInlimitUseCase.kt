package com.example.shoppingapp_user.Domain.UseCase.Home

import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class GetCategoryInlimitUseCase @Inject constructor(
    private val repo : repo
) {
    fun getCategoryInlimitUseCase() = repo.getCategoryInLimit()
}
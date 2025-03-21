package com.example.shoppingapp_user.Domain.UseCase.Category

import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class GetAllCategoryUseCase @Inject constructor(val repo : repo) {

    fun getAllCategoryUseCase() = repo.getAllCategory()
}
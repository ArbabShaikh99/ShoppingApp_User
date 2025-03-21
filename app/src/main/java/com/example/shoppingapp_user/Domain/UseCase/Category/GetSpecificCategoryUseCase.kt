package com.example.shoppingapp_user.Domain.UseCase.Category

import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class GetSpecificCategoryUseCase @Inject constructor(
    private val repo : repo
) {
    fun getSpecificCategoryUseCase(categoryName :String) = repo.getSpecificCategory(categoryName)
}
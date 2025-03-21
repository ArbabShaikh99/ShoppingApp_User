package com.example.shoppingapp_user.Domain.UseCase.Favouite

import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class GetAllFavProductUseCase @Inject constructor(
    private val repo : repo
){
    fun getAllFavProductUseCase() = repo.getAllFavouiteProduct()
}
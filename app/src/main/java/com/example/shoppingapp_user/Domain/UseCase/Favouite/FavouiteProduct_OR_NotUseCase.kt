package com.example.shoppingapp_user.Domain.UseCase.Favouite

import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class FavouiteProduct_OR_NotUseCase  @Inject constructor(
    private val repo : repo
){
    fun favouiteProduct_OR_NotUseCase(productId : String) =
        repo.FavouiteProductORNot(productId)
}
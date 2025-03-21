package com.example.shoppingapp_user.Domain.UseCase.Favouite

import com.example.shoppingapp_user.Domain.Models.getAllProductsModel
import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class FavouriteProductUseCase @Inject constructor(
    private val repo : repo
) {
    fun favouriteProductUseCase(productModel : getAllProductsModel) =
        repo.FavouiteProduct(productModel)
}
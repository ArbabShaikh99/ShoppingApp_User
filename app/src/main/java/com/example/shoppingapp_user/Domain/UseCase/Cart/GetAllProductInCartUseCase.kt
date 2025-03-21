package com.example.shoppingapp_user.Domain.UseCase.Cart

import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class GetAllProductInCartUseCase @Inject constructor(
    private val repo : repo
) {
    fun getAllProductInCartUseCase() = repo.getAllProductInCart()
}
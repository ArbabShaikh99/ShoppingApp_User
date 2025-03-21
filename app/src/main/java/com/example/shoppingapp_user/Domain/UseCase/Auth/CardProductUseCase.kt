package com.example.shoppingapp_user.Domain.UseCase.Auth

import com.example.shoppingapp_user.Domain.Models.CartModel
import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class CardProductUseCase @Inject constructor(
    private val repo : repo
) {
    fun cardProductUseCase(cartModel : CartModel) = repo.CardProduct(cartModel)
}
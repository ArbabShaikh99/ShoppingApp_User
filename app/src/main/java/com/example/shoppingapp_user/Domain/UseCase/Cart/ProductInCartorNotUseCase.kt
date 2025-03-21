package com.example.shoppingapp_user.Domain.UseCase.Cart

import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class ProductInCartorNotUseCase  @Inject constructor(
    private val repo: repo
){
    fun productInCartorNotUseCase(productId :String) = repo.ProductInCartorNot(productId)
}
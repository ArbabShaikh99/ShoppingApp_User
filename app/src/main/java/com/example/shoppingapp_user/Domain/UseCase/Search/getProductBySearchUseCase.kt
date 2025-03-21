package com.example.shoppingapp_user.Domain.UseCase.Search

import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class GetProductBySearchUseCase  @Inject constructor(
    private val repo: repo
){
    fun getProductBySearchUseCase(query:String) = repo.searchProduct(query)
}
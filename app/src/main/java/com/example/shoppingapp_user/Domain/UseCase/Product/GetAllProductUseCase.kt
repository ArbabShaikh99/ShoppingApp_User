package com.example.shoppingapp_user.Domain.UseCase.Product

import com.example.shoppingapp_user.Common.ResultState
import com.example.shoppingapp_user.Domain.Models.getAllProductsModel
import com.example.shoppingapp_user.Domain.repository.repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductUseCase @Inject constructor(val repo :repo) {

     fun getAllProductUseCase(): Flow<ResultState<List<getAllProductsModel>>> {
         return repo.getAllProducts()

     }
}
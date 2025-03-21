package com.example.shoppingapp_user.Domain.UseCase.Order

import com.example.shoppingapp_user.Domain.Models.OrderModel
import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class OrderUseCase @Inject constructor(
    private val repo : repo
){
    fun orderDataSaveUseCase(orderModel: List<OrderModel>) = repo.OrderSave(orderModel)
    fun getAllOrderUseCase()= repo.getAllOrder()
}
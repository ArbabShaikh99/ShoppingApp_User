package com.example.shoppingapp_user.Domain.UseCase.Shipping

import com.example.shoppingapp_user.Domain.Models.ShippingModel
import com.example.shoppingapp_user.Domain.repository.repo
import javax.inject.Inject

class ShippingUseCase @Inject constructor(
    private val repo : repo
){

    fun shippingDataSaveUseCase(shippingModel: ShippingModel) = repo.ShippingDataSave(shippingModel)
    fun shippingDataGetThroughUIDUseCase() = repo.shippingDataGetThroughUID()
}
package com.example.shoppingapp_user.Presentation_layer.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp_user.Common.ResultState
import com.example.shoppingapp_user.Domain.Models.ShippingModel
import com.example.shoppingapp_user.Domain.UseCase.Shipping.ShippingUseCase
import com.example.shoppingapp_user.Domain.repository.repo
import com.example.shoppingapp_user.Presentation_layer.Screens.Shiping.ScreenState.ShippingScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShippingViewModel @Inject constructor(
    private val ShippingUseCase : ShippingUseCase
): ViewModel() {

    private val _shippingScreenState = MutableStateFlow(ShippingScreenState())
    val shippingScreenState = _shippingScreenState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ShippingScreenState()
    )

    private val _shippingState = MutableStateFlow(ShippingState())
    val shippingState = _shippingState.asStateFlow()

    private val _getShippingState = MutableStateFlow(GetShippingDataState())
    val getShippingState = _getShippingState.asStateFlow()

    fun shippingDataSave(shippingModel: ShippingModel){
        viewModelScope.launch {
            ShippingUseCase.shippingDataSaveUseCase(shippingModel).collect {
                when(it){
                    is ResultState.Loading -> {
                        _shippingState.value = ShippingState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _shippingState.value = ShippingState(data = it.data)
                    }
                    is ResultState.Error -> {
                        _shippingState.value = ShippingState(error = it.error)
                    }
                }
            }
        }
    }
    fun getShippingDataThroughUID(){
        viewModelScope.launch {
            ShippingUseCase.shippingDataGetThroughUIDUseCase().collect{
                when(it){
                    is ResultState.Loading -> {
                        _getShippingState.value = GetShippingDataState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _getShippingState.value = GetShippingDataState(data = it.data)
                    }
                    is ResultState.Error -> {
                        _getShippingState.value = GetShippingDataState(error = it.error)
                    }
                }
            }
        }
    }



}
data class ShippingState(
    val isLoading : Boolean = false,
    val data : String = "",
    val error : String = ""
)
data class GetShippingDataState(
    val isLoading : Boolean = false,
    val data : ShippingModel? = null,
    val error : String = ""
)
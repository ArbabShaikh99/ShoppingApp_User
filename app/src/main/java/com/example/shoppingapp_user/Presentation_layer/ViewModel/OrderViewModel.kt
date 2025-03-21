package com.example.shoppingapp_user.Presentation_layer.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp_user.Common.ResultState
import com.example.shoppingapp_user.Domain.Models.OrderModel
import com.example.shoppingapp_user.Domain.UseCase.Order.OrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private  val OrderUseCase : OrderUseCase) :ViewModel() {

    private val _orderState = MutableStateFlow(OrderState())
    val orderState = _orderState.asStateFlow()

    private val _paymentState = MutableStateFlow(PaymentState())
    val paymentState = _paymentState.asStateFlow()

    private val _getAllOrderDataState = MutableStateFlow(GetAllOrderDataState())
    val getAllOrderDataState = _getAllOrderDataState.asStateFlow()


    fun orderDataSave(orderList: List<OrderModel>){
        viewModelScope.launch {
            OrderUseCase.orderDataSaveUseCase(orderList).collect {
                when(it){
                    is ResultState.Loading -> {
                        _orderState.value = OrderState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _orderState.value = OrderState(data = it.data)
                    }
                    is ResultState.Error -> {
                        _orderState.value = OrderState(error = it.error)
                    }
                }
            }
        }
    }
    fun setPaymentState(paymentId : String = "",errorMsg: String = "") {
        if(errorMsg.isEmpty()) {
            _paymentState.value = PaymentState(
                isLoading = false,
                paymentState = paymentId,
                error = ""
            )
        }else{
            _paymentState.value = PaymentState(
                isLoading = false,
                paymentState = "",
                error = errorMsg
            )
        }
    }
    fun getAllOrderState(){
        viewModelScope.launch {
            OrderUseCase.getAllOrderUseCase().collect{
                when(it) {
                    is ResultState.Loading -> {
                        _getAllOrderDataState.value = GetAllOrderDataState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _getAllOrderDataState.value = GetAllOrderDataState(data = it.data)
                    }
                    is ResultState.Error -> {
                        _getAllOrderDataState.value = GetAllOrderDataState(error = it.error)
                    }
                }
            }
        }
    }
    fun clearOrderState(){
        _orderState.value = OrderState()
    }
    fun clearPaymentState(){
        _paymentState.value = PaymentState()
    }

}
data class OrderState(
    val isLoading : Boolean = false,
    val data : String = "",
    val error : String = ""
)
data class PaymentState(
    val isLoading : Boolean = false,
    val paymentState : String = "",
    val error : String = ""
)
data class GetAllOrderDataState(
    val isLoading : Boolean = false,
    val data : List<OrderModel> = emptyList(),
    val error : String = ""
)
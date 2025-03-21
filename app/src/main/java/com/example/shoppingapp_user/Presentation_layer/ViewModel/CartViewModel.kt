package com.example.shoppingapp_user.Presentation_layer.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp_user.Common.ResultState
import com.example.shoppingapp_user.Domain.Models.CartModel
import com.example.shoppingapp_user.Domain.Models.getAllProductsModel
import com.example.shoppingapp_user.Domain.UseCase.Auth.CardProductUseCase
import com.example.shoppingapp_user.Domain.UseCase.Cart.GetAllProductInCartUseCase
import com.example.shoppingapp_user.Domain.UseCase.Cart.ProductInCartorNotUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val CardProductUseCase : CardProductUseCase,
    private val ProductInCartorNotUseCase : ProductInCartorNotUseCase,
    private val GetAllProductInCartUseCase : GetAllProductInCartUseCase
) :ViewModel() {

       private  val _cartProductState = MutableStateFlow(CartProductState())
      val cartProductState = _cartProductState.asStateFlow()

    private val _productInCartorNotState = MutableStateFlow(ProductInCartorNotState())
    val productInCartorNotState = _productInCartorNotState.asStateFlow()

    private val _getAllProductInCartState = MutableStateFlow(GetAllProductInCartState())
     val getAllProductInCartState = _getAllProductInCartState.asStateFlow()


    fun CardProduct(cartModel :CartModel){
        viewModelScope.launch {
            CardProductUseCase.cardProductUseCase(cartModel).collectLatest {
                when(it){
                    is ResultState.Loading -> {
                        _cartProductState.value = CartProductState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _cartProductState.value =
                            CartProductState(data = it.data)
                    }

                    is ResultState.Error -> {
                        _cartProductState.value = CartProductState(error = it.error)
                    }
                }
            }
        }

    }


    fun ProductInCartorNot(productId:String) {
        viewModelScope.launch {
            ProductInCartorNotUseCase.productInCartorNotUseCase(productId).collectLatest {
                when (it) {
                    is ResultState.Loading -> {
                        _productInCartorNotState.value = ProductInCartorNotState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _productInCartorNotState.value =
                            ProductInCartorNotState(data = it.data)
                    }

                    is ResultState.Error -> {
                        _productInCartorNotState.value = ProductInCartorNotState(error = it.error)
                    }
                }
            }
        }
    }


    fun getAllProductInCart(){
        viewModelScope.launch(Dispatchers.IO) {
            GetAllProductInCartUseCase.getAllProductInCartUseCase().collectLatest {
                when(it){
                    is ResultState.Loading -> {
                        _getAllProductInCartState.value = GetAllProductInCartState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getAllProductInCartState.value =
                            GetAllProductInCartState(data = it.data)
                    }

                    is ResultState.Error -> {
                        _getAllProductInCartState.value = GetAllProductInCartState(error = it.error)
                    }
                }
            }
        }
    }

    fun resetProductInCartState(){
        _cartProductState.value = CartProductState()
        _getAllProductInCartState.value = GetAllProductInCartState()
        _productInCartorNotState.value = ProductInCartorNotState()
    }
}

data class GetAllProductInCartState(
    val isLoading : Boolean = false,
    val error : String ="",
    val data : List<CartModel> = emptyList()
)

data class ProductInCartorNotState(
    val isLoading: Boolean =false,
    val error: String =" ",
    val data : Boolean = false
)
data class CartProductState(
    val isLoading: Boolean =false,
    val error: String ="",
    val data: String = ""
)
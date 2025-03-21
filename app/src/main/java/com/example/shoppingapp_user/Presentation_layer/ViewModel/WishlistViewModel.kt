package com.example.shoppingapp_user.Presentation_layer.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp_user.Common.ResultState
import com.example.shoppingapp_user.Domain.Models.getAllProductsModel
import com.example.shoppingapp_user.Domain.UseCase.Favouite.FavouiteProduct_OR_NotUseCase
import com.example.shoppingapp_user.Domain.UseCase.Favouite.FavouriteProductUseCase
import com.example.shoppingapp_user.Domain.UseCase.Favouite.GetAllFavProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val GetAllFavProductUseCase: GetAllFavProductUseCase,
    private val FavouiteProduct_OR_NotUseCase : FavouiteProduct_OR_NotUseCase,
    private val FavouriteProductUseCase : FavouriteProductUseCase
) :ViewModel() {

    private val _favouriteProductState = MutableStateFlow(FavouriteProductState())
    val favouriteProductState = _favouriteProductState.asStateFlow()

    private val _checkProductInFavouriteOrNotState = MutableStateFlow(CheckProductInFavouriteOrNotState())
    val checkProductInFavouriteOrNotState = _checkProductInFavouriteOrNotState.asStateFlow()

    private val _getAllFavProductState  = MutableStateFlow(GetAllFavProductState())
    val getAllFavProductState = _getAllFavProductState.asStateFlow()

    fun getAllFavProduct(){
        viewModelScope.launch {
            GetAllFavProductUseCase.getAllFavProductUseCase().collectLatest {
                when(it){
                    is ResultState.Loading -> {
                        _getAllFavProductState.value = GetAllFavProductState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getAllFavProductState.value =
                            GetAllFavProductState(data = it.data)
                    }

                    is ResultState.Error -> {
                        _getAllFavProductState.value = GetAllFavProductState(error = it.error)
                    }
                }
            }
        }
    }

    fun checkProductInFavouriteOrNot(ProductId :String){
        viewModelScope.launch {
            FavouiteProduct_OR_NotUseCase.favouiteProduct_OR_NotUseCase(ProductId).collectLatest {
                when(it){
                    is ResultState.Loading -> {
                        _checkProductInFavouriteOrNotState.value = CheckProductInFavouriteOrNotState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _checkProductInFavouriteOrNotState.value =
                            CheckProductInFavouriteOrNotState(data = it.data)
                    }

                    is ResultState.Error -> {
                        _checkProductInFavouriteOrNotState.value = CheckProductInFavouriteOrNotState(error = it.error)
                    }
                }
            }
        }
    }

    fun FavouriteProduct(ProductModel :getAllProductsModel){
        viewModelScope.launch {
            FavouriteProductUseCase.favouriteProductUseCase(ProductModel).collectLatest {
                when(it){
                    is ResultState.Loading -> {
                        _favouriteProductState.value = FavouriteProductState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _favouriteProductState.value =
                            FavouriteProductState(data = it.data)
                    }

                    is ResultState.Error -> {
                        _favouriteProductState.value = FavouriteProductState(error = it.error)
                    }
                }

            }
        }

    }

    fun resetProductInFavouite(){
        _favouriteProductState.value = FavouriteProductState()
        _getAllFavProductState.value = GetAllFavProductState()
        _checkProductInFavouriteOrNotState.value = CheckProductInFavouriteOrNotState()
    }
}

data class GetAllFavProductState(
    val isLoading : Boolean = false,
    val error : String ="",
    val data : List<getAllProductsModel> = emptyList()
)

data class CheckProductInFavouriteOrNotState(
    val isLoading: Boolean =false,
    val error: String =" ",
    val data : Boolean = false
)

data class FavouriteProductState(
    val isLoading: Boolean =false,
    val error: String ="",
    val data: String = ""
)

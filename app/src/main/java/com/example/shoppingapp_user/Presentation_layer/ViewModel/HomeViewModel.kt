package com.example.shoppingapp_user.Presentation_layer.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp_user.Common.ResultState
import com.example.shoppingapp_user.Domain.Models.categoryModel
import com.example.shoppingapp_user.Domain.Models.getAllProductsModel
import com.example.shoppingapp_user.Domain.UseCase.Home.GetCategoryInlimitUseCase
import com.example.shoppingapp_user.Domain.UseCase.Home.GetProductInLimitedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val GetCategoryInlimitUseCase : GetCategoryInlimitUseCase ,
    private val GetProductInLimitedUseCase : GetProductInLimitedUseCase
) : ViewModel() {

  private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()

 init {
     HomeSeenLoad()
 }
    fun HomeSeenLoad(){
      viewModelScope.launch (Dispatchers.IO){
      combine(
          GetCategoryInlimitUseCase.getCategoryInlimitUseCase(),
          GetProductInLimitedUseCase.getProductInLimited()
      ){ categoriesResult, productsResult ->
       when{
           categoriesResult is ResultState.Error ->
               HomeScreenState(isLoading = false, errorMessage = categoriesResult.error)
               productsResult is ResultState.Error ->
                   HomeScreenState(isLoading = false, errorMessage = productsResult.error)

           categoriesResult is ResultState.Success && productsResult is ResultState.Success ->
               HomeScreenState(
                   isLoading = false ,
                   categories = categoriesResult.data,
                   products = productsResult.data,
               )
           else -> HomeScreenState(isLoading = true)

       }
       }.collect{ state ->
           _homeScreenState.value = state
      }
      }
    }

}

data class HomeScreenState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val categories: List<categoryModel>? = null,
    val products: List<getAllProductsModel>? = null,
)
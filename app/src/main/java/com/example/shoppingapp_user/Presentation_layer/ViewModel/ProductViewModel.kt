package com.example.shoppingapp_user.Presentation_layer.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp_user.Common.ResultState
import com.example.shoppingapp_user.Domain.Models.getAllProductsModel
import com.example.shoppingapp_user.Domain.UseCase.Product.GetAllProductUseCase
import com.example.shoppingapp_user.Domain.UseCase.Product.GetSpecificProductIDUseCase
import com.example.shoppingapp_user.Domain.UseCase.Search.GetProductBySearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val GetAllProdctUseCase : GetAllProductUseCase,
    private val GetSpecificProductID : GetSpecificProductIDUseCase,
    private val GetProductBySearchUseCase : GetProductBySearchUseCase
): ViewModel(){

    private val _getAllProductState = MutableStateFlow(GetAllProductState())
    val getAllProductState = _getAllProductState.asStateFlow()

    private val _getProductByIDState = MutableStateFlow(GetProductByIDState())
    val getProductByIDState = _getProductByIDState.asStateFlow()


    private val _searchProductState = MutableStateFlow(SearchProductState())
   val searchProductState = _searchProductState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        observeSearchQuery()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery.debounce(500L)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isNotEmpty()) {
                        searchProduct(query)
                    } else {
                        _searchProductState.value = SearchProductState(data = emptyList())
                    }
                }
        }
    }

    private fun searchProduct(query: String) {
        viewModelScope.launch {
            GetProductBySearchUseCase.getProductBySearchUseCase(query)
                .collectLatest { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            _searchProductState.value = SearchProductState(isLoading = true)
                        }

                        is ResultState.Success -> {
                            _searchProductState.value = SearchProductState(data = result.data)
                        }

                        is ResultState.Error -> {
                            _searchProductState.value = SearchProductState(error = result.error)
                        }
                    }
                }
        }
    }




//    private val _searchProductState = MutableStateFlow(SearchProductState())
//    val searchProductState = _searchProductState.asStateFlow()
//
//    val _searchQuery = MutableStateFlow("")
//    fun onSearchQeryChnage(query :String){
//        _searchQuery.value = query
//    }
//
//    fun searchQuery(){
//        viewModelScope.launch {
//            _searchQuery.debounce(500L).distinctUntilChanged()
//                .collect{
//                    if (it.isNotEmpty()){
//                        searchProduct(query = it)
//                    }
//                }
//        }
//    }
//
//    fun searchProduct(query:String){
//        viewModelScope.launch {
//            GetProductBySearchUseCase.getProductBySearchUseCase(query).collectLatest {
//                 when(it){
//                     is ResultState.Loading ->{
//                         _searchProductState.value = SearchProductState(isLoading = true)
//                     }
//                     is ResultState.Success ->{
//                         _searchProductState.value = SearchProductState(data = it.data)
//                     }
//                     is ResultState.Error ->{
//                         _searchProductState.value = SearchProductState(error = it.error)
//                     }
//                 }
//            }
//        }
//    }


    fun getAllProduct(){
        viewModelScope.launch {
            GetAllProdctUseCase.getAllProductUseCase().collectLatest {
                when(it){
                    is ResultState.Loading ->{
                        _getAllProductState.value = GetAllProductState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _getAllProductState.value = GetAllProductState(data = it.data)
                    }
                    is ResultState.Error ->{
                        _getAllProductState.value = GetAllProductState(error = it.error)
                    }
                }
            }
        }
    }

    fun getProductByID(productID:String){
        viewModelScope.launch {
            GetSpecificProductID.getSpecificProductIDUseCase(productID).collectLatest {
                when(it){
                    is ResultState.Loading -> {
                        _getProductByIDState.value = GetProductByIDState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _getProductByIDState.value = GetProductByIDState(data = it.data)
                    }
                    is ResultState.Error ->{
                        _getProductByIDState.value = GetProductByIDState(error = it.error)
                    }
                }
            }
        }
    }


}


data class GetAllProductState(
    val isLoading :Boolean =false,
    val error :String ="",
    val data:List<getAllProductsModel> = emptyList()
)

data class GetProductByIDState(
    val isLoading: Boolean =false,
    val error :String? = null,
    val data : getAllProductsModel? = null
)

data class SearchProductState(
    val isLoading: Boolean = false,
    val data: List<getAllProductsModel> = emptyList(),
    val error :String? = null,
)
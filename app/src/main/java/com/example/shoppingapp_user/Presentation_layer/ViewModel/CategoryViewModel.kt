package com.example.shoppingapp_user.Presentation_layer.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp_user.Common.ResultState
import com.example.shoppingapp_user.Domain.Models.categoryModel
import com.example.shoppingapp_user.Domain.Models.getAllProductsModel
import com.example.shoppingapp_user.Domain.UseCase.Category.GetAllCategoryUseCase
import com.example.shoppingapp_user.Domain.UseCase.Category.GetSpecificCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val GetSpecificCategoryUseCase: GetSpecificCategoryUseCase

    )
    :ViewModel() {

     private  val _getAllCategoryStat = MutableStateFlow(GetCategoryState())
    val getAllCategoryStat = _getAllCategoryStat.asStateFlow()

    private val _getSpecificCategoryState = MutableStateFlow(GetSpecificCategoryState())
    val getSpecificCategoryState = _getSpecificCategoryState.asStateFlow()



    fun getSpecificCategory(categoryName: String){
        viewModelScope.launch {
            GetSpecificCategoryUseCase.getSpecificCategoryUseCase(categoryName).collectLatest {
                when(it){
                    is ResultState.Loading ->{
                        _getSpecificCategoryState.value = GetSpecificCategoryState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _getSpecificCategoryState.value = GetSpecificCategoryState(data = it.data)
                    }
                    is ResultState.Error ->{
                        _getSpecificCategoryState.value = GetSpecificCategoryState(error = it.error)
                    }
                }
            }
        }
    }

    fun getAllCategory(){
        viewModelScope.launch {
            getAllCategoryUseCase.getAllCategoryUseCase().collectLatest {
                when(it){
                    is ResultState.Loading ->{
                      _getAllCategoryStat.value = GetCategoryState(isLoading = true)
                    }
                    is ResultState.Success ->{
                       _getAllCategoryStat.value = GetCategoryState(data = it.data)
                    }
                    is ResultState.Error ->{
                        _getAllCategoryStat.value = GetCategoryState(error = it.error)
                    }
                }
            }
        }
    }




}

data class GetCategoryState(
    val isLoading :Boolean =false,
    val error :String ="",
    val data:List<categoryModel> = emptyList()
)
data class GetSpecificCategoryState(
    val isLoading: Boolean =false,
    val error: String = "",
    val data: List<getAllProductsModel> = emptyList()
)
package com.example.shoppingapp_user.Presentation_layer.ViewModel

import android.net.Uri
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppingapp_user.Common.ResultState
import com.example.shoppingapp_user.Domain.Models.UserDataModel
import com.example.shoppingapp_user.Domain.UseCase.User.GetUserProfileDataUseCase
import com.example.shoppingapp_user.Domain.UseCase.User.InsertProfileImageUseCase
import com.example.shoppingapp_user.Domain.UseCase.User.UpdateUserDataUseCase
import com.example.shoppingapp_user.Domain.UseCase.User.UploadUserImageUseCase
import com.example.shoppingapp_user.Presentation_layer.Screens.Profile.ScreenState.getProfileScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
private val GetUserProfileDataUseCase : GetUserProfileDataUseCase,
 private val InsertProfileImageUseCase : InsertProfileImageUseCase,
 private val UpdateUserDataUseCase : UpdateUserDataUseCase,
 private val UploadUserImageUseCase: UploadUserImageUseCase

) :ViewModel() {

    private val _getUserProfileState = MutableStateFlow(GetProfileDataState())
    val getUserProfileState = _getUserProfileState.asStateFlow()

    private val  _getProfileUserScreenState = MutableStateFlow(getProfileScreenState())
    val getProfileUserScreenState =  _getProfileUserScreenState.stateIn(
        scope = viewModelScope ,
        started = SharingStarted.WhileSubscribed(),
        initialValue = getProfileScreenState()
    )

    private val _insertProfileImageState = MutableStateFlow(InsertProfileImageState())
    val insertProfileImageState = _insertProfileImageState.asStateFlow()

    private val _updateUserDataState = MutableStateFlow(UpdateUserDataState())
    val updateUserDataState = _updateUserDataState.asStateFlow()

    private val _updateUserImageState = MutableStateFlow(UpdateUserImageState())
    val updateUserImageState = _updateUserImageState.asStateFlow()


    fun getProfileData(UserID: String){
        viewModelScope.launch {
            GetUserProfileDataUseCase.getUserProfileDataUseCase(UserID).collectLatest {
                when(it){
                    is ResultState.Loading ->{
                        _getUserProfileState.value = GetProfileDataState(isloding = true)
                    }
                    is ResultState.Success ->{
                        _getUserProfileState.value = GetProfileDataState(data = it.data)
                    }
                    is ResultState.Error ->{
                        _getUserProfileState.value = GetProfileDataState(error = it.error)
                    }
                }
            }
        }
    }

    fun userProfileImage(imageUri : Uri){
        viewModelScope.launch {
            InsertProfileImageUseCase.insertProfileImageUseCase(imageUri).collectLatest {
                when(it){
                    is ResultState.Loading ->{
                        _insertProfileImageState.value = InsertProfileImageState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _insertProfileImageState.value = InsertProfileImageState(data = it.data)
                    }
                    is ResultState.Error ->{
                        _insertProfileImageState.value = InsertProfileImageState(error = it.error)
                    }

                }
            }
        }
    }

    fun updateUserData(userData : UserDataModel){
        viewModelScope.launch {
            UpdateUserDataUseCase.updateUserDataUseCase(userData).collectLatest {
                when(it){
                    is ResultState.Loading ->{
                        _updateUserDataState.value = UpdateUserDataState(isloding = true)
                    }
                    is ResultState.Success ->{
                        _updateUserDataState.value = UpdateUserDataState(data = it.data)
                    }
                    is ResultState.Error ->{
                        _updateUserDataState.value = UpdateUserDataState(error = it.error)
                    }
                }
            }
        }
    }
    fun updateUserImage(userImage : Uri){
        viewModelScope.launch {
            UploadUserImageUseCase.updateUserDataUseCase(userImage).collectLatest {
                when(it){
                    is ResultState.Loading ->{
                        _updateUserImageState.value = UpdateUserImageState(isloding = true)
                    }
                    is ResultState.Success ->{
                        _updateUserImageState.value = UpdateUserImageState(data = it.data)
                    }
                    is ResultState.Error ->{
                        _updateUserImageState.value = UpdateUserImageState(error = it.error)
                    }
                }
            }
        }
    }
    fun clearUpdateProfileDataState() {
        _updateUserDataState.value = UpdateUserDataState()
        _insertProfileImageState.value = InsertProfileImageState()
    }

}

data class GetProfileDataState(
    val isloding :Boolean =false,
    val error : String ="",
    val data: UserDataModel? = null
)

data class InsertProfileImageState(
    val isLoading:Boolean =  false,
    val data : String="",
    val error: String = ""
)

data class UpdateUserDataState(
    val isloding: Boolean = false ,
    val data : String? = null,
    val error : String = ""
)
data class UpdateUserImageState(
    val isloding: Boolean = false ,
    val data : String? = null,
    val error : String = ""
)
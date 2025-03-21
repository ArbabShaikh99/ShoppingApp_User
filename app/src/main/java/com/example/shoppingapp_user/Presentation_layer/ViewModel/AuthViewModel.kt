package com.example.shoppingapp_user.Presentation_layer.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp_user.Common.ResultState
import com.example.shoppingapp_user.Domain.Models.UserDataModel
import com.example.shoppingapp_user.Domain.UseCase.Auth.RegisterUserWithEmailAndPasswordUseCase
import com.example.shoppingapp_user.Domain.UseCase.Auth.loginUserWithEmailAndPasswordUseCase
import com.example.shoppingapp_user.Presentation_layer.Screens.Auth.ScreenState.LoginScreenState
import com.example.shoppingapp_user.Presentation_layer.Screens.Auth.ScreenState.SignUpScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel   @Inject constructor(
    private val registerUserWithEmailAndPasswordUseCase: RegisterUserWithEmailAndPasswordUseCase,
    private val loginUserUseCase : loginUserWithEmailAndPasswordUseCase,
):ViewModel() {

    private val _loginUserState = MutableStateFlow(LoginState())
    val loginUserState = _loginUserState.asStateFlow()

    private val _userLoginScreenState = MutableStateFlow(LoginScreenState())
    val userLoginScreenState = _userLoginScreenState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = LoginScreenState()
    )

    private val  _userRegisterState  = MutableStateFlow(RegisterState())
    val userRegisterState  = _userRegisterState.asStateFlow()

    private val _userRegisterScreenState = MutableStateFlow(SignUpScreenState())
    val userRegisterScreenState = _userRegisterScreenState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SignUpScreenState()
    )




    fun registerUser(userItems : UserDataModel){
        viewModelScope.launch (Dispatchers.IO){
            registerUserWithEmailAndPasswordUseCase.registerUserWithEmailAndPasswordUseCase(userItems)
                .collectLatest {
                    when(it){
                        is ResultState.Loading ->{
                            _userRegisterState.value = RegisterState(isLoading = true)
                        }
                        is ResultState.Success ->{
                            _userRegisterState.value = RegisterState(data = it.data)
                        }
                        is ResultState.Error ->{
                            _userRegisterState.value = RegisterState(error = it.error)
                        }
                    }
                }
        }
    }

    fun loginUser(userItems : UserDataModel){
        viewModelScope.launch {
            loginUserUseCase.loginUserWithEmailAndPasswordUseCase(userItems)
                .collectLatest {
                    when(it){
                        is ResultState.Loading ->{
                            _loginUserState.value = LoginState(isLoading = true)
                        }
                        is ResultState.Success ->{
                            _loginUserState.value = LoginState(data = it.data)
                        }
                        is ResultState.Error ->{
                            _loginUserState.value = LoginState(error = it.error)
                        }
                    }
                }
        }

    }
    fun resetauthState(){
        _loginUserState.value = LoginState()
        _userLoginScreenState.value = LoginScreenState()
        _userRegisterState.value  = RegisterState()
        _userRegisterScreenState.value = SignUpScreenState()

    }
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

data class RegisterState(
    val isLoading: Boolean = false ,
    val error : String = "",
    val data : String =""
)
data class LoginState(
    val isLoading: Boolean = false ,
    val error : String = "",
    val data : String? =""
)
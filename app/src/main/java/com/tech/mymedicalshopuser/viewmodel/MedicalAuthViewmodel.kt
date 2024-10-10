package com.tech.mymedicalshopuser.viewmodel

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.mymedicalshopuser.data.response.signupLogin.SignupLoginResponse
import com.tech.mymedicalshopuser.domain.repository.MedicalRepository
import com.tech.mymedicalshopuser.state.MedicalResponseState
import com.tech.mymedicalshopuser.state.MedicalSignInScreenState
import com.tech.mymedicalshopuser.state.MedicalSignupScreenState
import com.tech.mymedicalshopuser.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MedicalAuthViewmodel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val medicalRepository: MedicalRepository
) : ViewModel() {

    private val preferenceManager = PreferenceManager(context)

    private val _signupResponseState =
        MutableStateFlow<MedicalResponseState<Response<SignupLoginResponse>>>(MedicalResponseState.Loading)
    val signupResponseState = _signupResponseState.asStateFlow()

    private val _loginResponseState =
        MutableStateFlow<MedicalResponseState<Response<SignupLoginResponse>>>(MedicalResponseState.Loading)
    val loginResponseState = _loginResponseState.asStateFlow()

    private val _signupScreenStateData = MutableStateFlow(MedicalSignupScreenState())
    val signupScreenStateData = _signupScreenStateData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MedicalSignupScreenState()
    )
    private val _loginScreenStateData = MutableStateFlow(MedicalSignInScreenState())
    val loginScreenStateData = _loginScreenStateData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MedicalSignInScreenState()
    )

    fun signupUser(
        name: String,
        email: String,
        phoneNumber: String,
        password: String,
        address: String,
        pinCode: String
    ) {
        viewModelScope.launch {
            try {
                _signupResponseState.value = MedicalResponseState.Loading
                val signupResponse = medicalRepository.signupUser(
                    name = name,
                    email = email,
                    phoneNumber = phoneNumber,
                    password = password,
                    address = address,
                    pinCode = pinCode
                )
                _signupResponseState.value = MedicalResponseState.Success(signupResponse)

            } catch (e: Exception) {
                _signupResponseState.value = MedicalResponseState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun loginUser(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                _loginResponseState.value = MedicalResponseState.Loading
                val loginResponse = medicalRepository.loginUser(
                    email = email,
                    password = password
                )
                _loginResponseState.value = MedicalResponseState.Success(loginResponse)
            } catch (e: Exception) {
                _loginResponseState.value = MedicalResponseState.Error(e.message ?: "An error occurred")
            }
        }
    }
    fun resetSignupStateData() {
        _signupScreenStateData.value = MedicalSignupScreenState(
            userName = mutableStateOf(""),
            mobileNo = mutableStateOf(""),
            email = mutableStateOf(""),
            password = mutableStateOf(""),
            address = mutableStateOf(""),
        )
        val sharedPreferencesSignup = context.getSharedPreferences("SignupPrefs", MODE_PRIVATE)
//        val sharedPreferencesLogin = context.getSharedPreferences("LoginPrefs", MODE_PRIVATE)
        sharedPreferencesSignup.edit().clear().apply()
//        sharedPreferencesLogin.edit().clear().apply()
    }
    fun resetLoginStateData() {
        _loginScreenStateData.value = MedicalSignInScreenState(
            email = mutableStateOf(""),
            password = mutableStateOf("")
        )
    }
    fun saveSignUpStateDataBeforeDestroyScreen(){
        preferenceManager.saveSignupData(
            signupScreenState = signupScreenStateData.value
        )
    }
    fun loadSignUpStateDataAfterDestroyScreen(){
        _signupScreenStateData.value = preferenceManager.loadSignupData()
    }
    fun saveLoginStateDataBeforeDestroyScreen(){
        preferenceManager.saveLoginData(
            loginScreenState = loginScreenStateData.value
        )
    }
    fun loadLoginStateDataAfterDestroyScreen(){
        _loginScreenStateData.value = preferenceManager.loadLoginData()
    }

}
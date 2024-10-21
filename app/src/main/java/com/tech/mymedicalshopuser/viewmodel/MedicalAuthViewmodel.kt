package com.tech.mymedicalshopuser.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.mymedicalshopuser.domain.repository.MedicalRepository
import com.tech.mymedicalshopuser.state.MedicalAuthResponseSate
import com.tech.mymedicalshopuser.state.MedicalResponseState
import com.tech.mymedicalshopuser.state.screen_state.MedicalSignInScreenState
import com.tech.mymedicalshopuser.state.screen_state.MedicalSignupScreenState
import com.tech.mymedicalshopuser.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicalAuthViewmodel @Inject constructor(
    private val medicalRepository: MedicalRepository,
) : ViewModel() {

    private val _signupResponseState = MutableStateFlow(MedicalAuthResponseSate())
    val signupResponseState = _signupResponseState.asStateFlow()

    private val _loginResponseState = MutableStateFlow(MedicalAuthResponseSate())
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
            medicalRepository.signupUser(
                name = name,
                email = email,
                phoneNumber = phoneNumber,
                password = password,
                address = address,
                pinCode = pinCode
            ).collect {
                when (it) {
                    is MedicalResponseState.Loading -> {
                        _signupResponseState.value = MedicalAuthResponseSate(isLoading = true)
                    }

                    is MedicalResponseState.Success -> {
                        _signupResponseState.value = MedicalAuthResponseSate(data = it.data.body())
                    }

                    is MedicalResponseState.Error -> {
                        _signupResponseState.value = MedicalAuthResponseSate(error = it.message)
                    }
                }
            }
        }
    }

    fun loginUser(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            medicalRepository.loginUser(
                email = email,
                password = password
            ).collect {
                when (it) {
                    is MedicalResponseState.Loading -> {
                        _loginResponseState.value = MedicalAuthResponseSate(isLoading = true)
                    }

                    is MedicalResponseState.Success -> {
                        _loginResponseState.value = MedicalAuthResponseSate(data = it.data.body())
                    }

                    is MedicalResponseState.Error -> {
                        _loginResponseState.value = MedicalAuthResponseSate(error = it.message)
                    }
                }
            }
        }
    }

    fun resetSignupScreenStateData() {
        _signupScreenStateData.value = MedicalSignupScreenState(
            userName = mutableStateOf(""),
            mobileNo = mutableStateOf(""),
            email = mutableStateOf(""),
            password = mutableStateOf(""),
            address = mutableStateOf(""),
        )
    }

    fun resetLoginScreenStateData() {
        _loginScreenStateData.value = MedicalSignInScreenState(
            email = mutableStateOf(""),
            password = mutableStateOf("")
        )
    }
    fun clearLoginResponseData(){
        _loginResponseState.value = MedicalAuthResponseSate(
            isLoading = false,
            data = null,
            error = null
        )
    }
}
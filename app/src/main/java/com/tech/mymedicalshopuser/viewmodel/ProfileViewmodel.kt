package com.tech.mymedicalshopuser.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.mymedicalshopuser.domain.repository.MedicalRepository
import com.tech.mymedicalshopuser.state.MedicalGetAllUserResponseState
import com.tech.mymedicalshopuser.state.MedicalProductResponseState
import com.tech.mymedicalshopuser.state.MedicalResponseState
import com.tech.mymedicalshopuser.state.UpdatedProfileResponseSate
import com.tech.mymedicalshopuser.state.screen_state.ProfileScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor(
    private val medicalRepository: MedicalRepository
) : ViewModel() {

    private val _getSpecificUser =
        MutableStateFlow(MedicalGetAllUserResponseState())
    val getSpecificUser = _getSpecificUser.asStateFlow()

    private val _updateProfileUserData =
        MutableStateFlow(UpdatedProfileResponseSate())
    val updateProfileResponseState = _updateProfileUserData.asStateFlow()

    private val _profileScreenStateUserData = MutableStateFlow(ProfileScreenState())
    val profileScreenStateUserData = _profileScreenStateUserData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileScreenState()
    )

    private val _getAllProducts = MutableStateFlow(MedicalProductResponseState())
    val getAllProducts = _getAllProducts.asStateFlow()

    init {
        getAllProducts()
    }
    fun getSpecificUser(userId: String) {
        viewModelScope.launch {
          medicalRepository.getSpecificUser(userId).collect{
              when(it){
                  is MedicalResponseState.Loading->{
                      _getSpecificUser.value = MedicalGetAllUserResponseState(isLoading = true)
                  }
                  is MedicalResponseState.Success->{
                      _getSpecificUser.value = MedicalGetAllUserResponseState(data = it.data.body())
                  }
                  is MedicalResponseState.Error->{
                      _getSpecificUser.value = MedicalGetAllUserResponseState(error = it.message)
                  }
              }
          }

        }
    }
    private fun getAllProducts(){
        viewModelScope.launch {
            medicalRepository.getAllProducts().collect{
                when(it){
                    is MedicalResponseState.Loading->{
                        _getAllProducts.value = MedicalProductResponseState(isLoading = true)
                    }
                    is MedicalResponseState.Success->{
                        _getAllProducts.value = MedicalProductResponseState(data = it.data.body())
                    }
                    is MedicalResponseState.Error->{
                        _getAllProducts.value = MedicalProductResponseState(error = it.message)
                    }
                }
            }
        }
    }
    fun updateUserData(loginUserId: String) {
        viewModelScope.launch {
            Log.d("@Acc", "updateUserData: ${profileScreenStateUserData.value.userName.value}")
            Log.d("@Acc", "updateUserData: ${_profileScreenStateUserData.value.userName.value}")
            Log.d("@Acc", "updateUserData: ${profileScreenStateUserData.value.address.value}")
            Log.d("@Acc", "updateUserData: ${_profileScreenStateUserData.value.address.value}")
            medicalRepository.updateUserData(
                userId = loginUserId,
                userName = profileScreenStateUserData.value.userName.value,
                userEmail = profileScreenStateUserData.value.userEmail.value,
                userPhone = profileScreenStateUserData.value.userPhone.value,
                pinCode = profileScreenStateUserData.value.pinCode.value,
                address = profileScreenStateUserData.value.address.value,
                password = profileScreenStateUserData.value.password.value
            ).collect{
                when(it){
                    is MedicalResponseState.Loading->{
                        _updateProfileUserData.value = UpdatedProfileResponseSate(isLoading = true)
                    }
                    is MedicalResponseState.Success->{
                        _updateProfileUserData.value = UpdatedProfileResponseSate(data = it.data.body())
                    }
                    is MedicalResponseState.Error->{
                        _updateProfileUserData.value = UpdatedProfileResponseSate(error = it.message)
                    }
                }
            }
        }
    }
    fun resetProfileScreenStateData() {
        _profileScreenStateUserData.value = ProfileScreenState(
            userPhone = mutableStateOf(""),
            userEmail = mutableStateOf(""),
            userName = mutableStateOf(""),
            password = mutableStateOf(""),
            address = mutableStateOf(""),
            pinCode = mutableStateOf(""),
            dateOfCreationAccount = mutableStateOf("")
        )
    }
}
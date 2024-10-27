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
import com.tech.mymedicalshopuser.state.screen_state.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
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
    private val _searchTextFieldState = MutableStateFlow(SearchScreenState())
    val searchTextFieldState = _searchTextFieldState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchScreenState()
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
    fun updateUserData(loginUserId: String, userImageFile: MultipartBody.Part?) {
        viewModelScope.launch {
            Log.d("@Acc", "updateUserData: ${profileScreenStateUserData.value.userName.value}")
            Log.d("@Acc", "updateUserData: ${_profileScreenStateUserData.value.userName.value}")
            Log.d("@Acc", "updateUserData: ${profileScreenStateUserData.value.address.value}")
            Log.d("@Acc", "updateUserData: ${_profileScreenStateUserData.value.address.value}")
            Log.d("@Acc", "updateUserData userImageFile: ${userImageFile}")
            medicalRepository.updateUserData(
                userId = loginUserId.toRequestBody("text/plain".toMediaTypeOrNull()),
                userName = profileScreenStateUserData.value.userName.value.toRequestBody("text/plain".toMediaTypeOrNull()),
                userEmail = profileScreenStateUserData.value.userEmail.value.toRequestBody("text/plain".toMediaTypeOrNull()),
                userPhone = profileScreenStateUserData.value.userPhone.value.toRequestBody("text/plain".toMediaTypeOrNull()),
                pinCode = profileScreenStateUserData.value.pinCode.value.toRequestBody("text/plain".toMediaTypeOrNull()),
                address = profileScreenStateUserData.value.address.value.toRequestBody("text/plain".toMediaTypeOrNull()),
                password = profileScreenStateUserData.value.password.value.toRequestBody("text/plain".toMediaTypeOrNull()),
                userImage = userImageFile
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
            dateOfCreationAccount = mutableStateOf(""),
            userImage = mutableStateOf(null),
            userImageId = mutableStateOf("")
        )
    }
}
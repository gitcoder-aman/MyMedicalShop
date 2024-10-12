package com.tech.mymedicalshopuser.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.mymedicalshopuser.data.response.user.GetAllUsersResponseItem
import com.tech.mymedicalshopuser.domain.repository.MedicalRepository
import com.tech.mymedicalshopuser.state.MedicalResponseState
import com.tech.mymedicalshopuser.state.MedicalSignInScreenState
import com.tech.mymedicalshopuser.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(
    @ApplicationContext context: Context,
    private val medicalRepository: MedicalRepository
) : ViewModel() {

    val preferenceManager = PreferenceManager(context)

    private val _getSpecificUser =
        MutableStateFlow<MedicalResponseState<Response<ArrayList<GetAllUsersResponseItem>>>>(
            MedicalResponseState.Loading
        )
    val getSpecificUser = _getSpecificUser.asStateFlow()

    fun getSpecificUser(userId: String) {
        viewModelScope.launch {
            try {
                _getSpecificUser.value = MedicalResponseState.Loading
                val getSpecificUser = medicalRepository.getSpecificUser(userId)
                Log.d("@@viewmodel", "getSpecificUser: $getSpecificUser")
                _getSpecificUser.value = MedicalResponseState.Success(getSpecificUser)

            } catch (e: Exception) {
                _getSpecificUser.value =
                    MedicalResponseState.Error(e.message ?: "An error occurred")
                Log.d("@@viewmodel", "error: ${e.message}")

            }

        }
    }
}
package com.tech.mymedicalshopuser.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.mymedicalshopuser.domain.repository.MedicalRepository
import com.tech.mymedicalshopuser.state.MedicalGetAllUserResponseState
import com.tech.mymedicalshopuser.state.MedicalProductResponseState
import com.tech.mymedicalshopuser.state.MedicalResponseState
import com.tech.mymedicalshopuser.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(
    @ApplicationContext context: Context,
    private val medicalRepository: MedicalRepository
) : ViewModel() {

    val preferenceManager = PreferenceManager(context)

    private val _getSpecificUser =
        MutableStateFlow(MedicalGetAllUserResponseState())
    val getSpecificUser = _getSpecificUser.asStateFlow()

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
}
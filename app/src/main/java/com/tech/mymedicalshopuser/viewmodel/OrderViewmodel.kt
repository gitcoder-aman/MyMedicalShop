package com.tech.mymedicalshopuser.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.domain.repository.MedicalRepository
import com.tech.mymedicalshopuser.state.MedicalGetAllOrderState
import com.tech.mymedicalshopuser.state.MedicalOrderResponseState
import com.tech.mymedicalshopuser.state.MedicalResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewmodel @Inject constructor(
    private val medicalRepository: MedicalRepository
) : ViewModel() {

    private val _createOrder = MutableStateFlow(MedicalOrderResponseState())
    val createOrder = _createOrder.asStateFlow()

    private val _getAllUserOrders = MutableStateFlow(MedicalGetAllOrderState())
    val getAllUserOrders = _getAllUserOrders.asStateFlow()

    fun createOrder(
        orderList: List<MedicalOrderResponseItem>
    ) {
        viewModelScope.launch {
            medicalRepository.createOrder(orderList = orderList).collect {
                when (it) {
                    is MedicalResponseState.Loading -> {
                        _createOrder.value = MedicalOrderResponseState(isLoading = true)
                    }
                    is MedicalResponseState.Success -> {
                        _createOrder.value = MedicalOrderResponseState(data = it.data.body())
                    }
                    is MedicalResponseState.Error -> {
                        _createOrder.value = MedicalOrderResponseState(error = it.message)
                    }
                }
            }

        }
    }

    fun getAllUserOrders(
        userId: String
    ) {
        viewModelScope.launch {
            medicalRepository.getAllUserOrders(userId).collect {
                when (it) {
                    is MedicalResponseState.Loading -> {
                        _getAllUserOrders.value = MedicalGetAllOrderState(loading = true)
                    }
                    is MedicalResponseState.Success -> {
                        Log.d("@TAG", "getAllUserOrders: ${it.data.body()?.size}")
                        _getAllUserOrders.value = MedicalGetAllOrderState(data = it.data)
                    }
                    is MedicalResponseState.Error -> {
                        _getAllUserOrders.value = MedicalGetAllOrderState(error = it.message)
                    }
                }
            }
        }
    }
}
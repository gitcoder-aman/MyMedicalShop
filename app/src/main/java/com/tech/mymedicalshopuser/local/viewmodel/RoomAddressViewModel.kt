package com.tech.mymedicalshopuser.local.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.mymedicalshopuser.local.dao.AddressDao
import com.tech.mymedicalshopuser.local.entity.AddressEntity
import com.tech.mymedicalshopuser.state.screen_state.AddAddressScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomAddressViewModel @Inject constructor(
    private val addressDao: AddressDao
): ViewModel() {

    private val _getAddressScreenState = MutableStateFlow(AddAddressScreenState())
    val getAddressScreenState = _getAddressScreenState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AddAddressScreenState()
    )

    private val _addressList = MutableStateFlow<List<AddressEntity>>(emptyList())
    val addressList = _addressList.asStateFlow()

    init {
        getAllAddress()
    }
    fun addAddress(address : AddressEntity){
        viewModelScope.launch {
            addressDao.insertAddress(address)
        }
        getAllAddress()
    }

    fun deleteAddress(address : AddressEntity){
        viewModelScope.launch {
            addressDao.deleteAddress(address)
        }
        getAllAddress()
    }
    private fun getAllAddress(){
        viewModelScope.launch {
            addressDao.getAllAddress().collect{addressList->
                _addressList.value = addressList
                Log.d("@roomGetAllAddressList", "_cartList size: ${_addressList.value.size}")
            }
        }
    }

    fun resetAddressState() {
        _getAddressScreenState.value = AddAddressScreenState(
            address = mutableStateOf(""),
            city = mutableStateOf(""),
            state = mutableStateOf(""),
            street = mutableStateOf(""),
            phoneNo = mutableStateOf(""),
            pinCode = mutableStateOf(""),
            fullName = mutableStateOf("")
        )
    }


}
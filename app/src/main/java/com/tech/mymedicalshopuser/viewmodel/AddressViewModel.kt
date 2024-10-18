package com.tech.mymedicalshopuser.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.mymedicalshopuser.state.MedicalGetAllOrderState
import com.tech.mymedicalshopuser.state.screen_state.AddAddressScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class AddressViewModel :ViewModel(){

    private val _getAddressScreenState = MutableStateFlow(AddAddressScreenState())
    val getAddressScreenState = _getAddressScreenState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AddAddressScreenState()
    )
    private val _getAllAddressList = MutableStateFlow(ArrayList<AddAddressScreenState>())
    val getAllAddressList = _getAllAddressList.asStateFlow()


    fun addAddress(){
        _getAllAddressList.value.add(_getAddressScreenState.value)
    }
    fun removeAddress(addAddressScreenState: AddAddressScreenState){
        _getAllAddressList.value.remove(addAddressScreenState)
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
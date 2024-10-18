package com.tech.mymedicalshopuser.local.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.mymedicalshopuser.domain.model.ClientChoiceModelEntity
import com.tech.mymedicalshopuser.local.dao.CartDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomCartViewModel @Inject constructor(
    private val cartDao: CartDao
) : ViewModel() {

    private val _cartList = MutableStateFlow<List<ClientChoiceModelEntity>>(emptyList())
    val cartList = _cartList.asStateFlow()

    private val _subTotalPrice = MutableStateFlow(0.0f)
    val subTotalPrice = _subTotalPrice.asStateFlow()

    init {
        getAllCartList()
        Log.d("@roomGetAllCartList", "_cartListSize: ${_cartList.value.size}")

    }
    fun insertCartList(productItem: ClientChoiceModelEntity) {
        viewModelScope.launch {
            cartDao.insertCartList(productItem)
        }
        getAllCartList()
    }

    fun deleteAllCartList() {
        viewModelScope.launch {
            cartDao.deleteAllCartList()
        }
        getAllCartList()
    }

    fun updateCartList(productId : String, productQty: Int) {
        viewModelScope.launch {
            cartDao.updateCartList(productId, productQty)
        }
        getAllCartList()
    }

    fun deleteCartById(productId: String) {
        viewModelScope.launch {
            cartDao.deleteCartById(productId)
        }
        getAllCartList()
    }

     private fun getAllCartList() {
        viewModelScope.launch {
            cartDao.getAllCartList().collect{cartList->

                cartList.forEach {
                    Log.d("@roomGetAllCartList", "getAllCartList: ${it.product_name}")
                }

                _cartList.value = cartList
                getSubTotalPrice()
                Log.d("@roomGetAllCartList", "_cartList size: ${_cartList.value.size}")

            }
        }
    }

    private fun getSubTotalPrice(){
        _subTotalPrice.value = _cartList.value.sumOf { it.product_price * it.product_count }.toFloat()
        Log.d("@roomGetAllCartList", "getAllCartList: ${_subTotalPrice.value}")
        Log.d("@roomGetAllCartList", "getAllCartList: ${subTotalPrice.value}")
    }
    fun findProductById(productId: String): ClientChoiceModelEntity? {
        getAllCartList()
        return _cartList.value.firstOrNull { it.product_id == productId }

    }


}
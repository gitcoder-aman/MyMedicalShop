package com.tech.mymedicalshopuser.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.tech.mymedicalshopuser.domain.model.ClientChoiceModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewmodel : ViewModel() {

    private val _cartItemsList = mutableStateListOf<ClientChoiceModel>()
    val cartItemsList: SnapshotStateList<ClientChoiceModel> = _cartItemsList

    private val _subTotalPrice = MutableStateFlow(0.0f)
    val subTotalPrice = _subTotalPrice.asStateFlow()

    fun addItem(product: ClientChoiceModel) {
        _cartItemsList.add(product)
        getSubTotalPrice() // Update subtotal
    }

    fun removeItem(product: ClientChoiceModel) {
        _cartItemsList.remove(product)
        getSubTotalPrice() // Update subtotal
    }

    fun clearCart() {
        _cartItemsList.clear()
        getSubTotalPrice() // Update subtotal
    }
    fun updateItemCount(clientChoiceModel: ClientChoiceModel, newCount: Int) {

        val cartIndex = _cartItemsList.indexOf(clientChoiceModel)
        if (cartIndex > -1  && newCount > 0) {
            _cartItemsList[cartIndex].product_count = newCount
        }
        getSubTotalPrice() // Update subtotal
    }

    private fun getSubTotalPrice(){
        _subTotalPrice.value = cartItemsList.sumOf { it.product_price * it.product_count }.toFloat()
    }

    fun findProductById(productId: String): ClientChoiceModel? {
        return _cartItemsList.firstOrNull { it.product_id == productId }
    }


}
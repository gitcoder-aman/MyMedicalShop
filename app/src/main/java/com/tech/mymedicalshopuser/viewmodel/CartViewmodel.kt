package com.tech.mymedicalshopuser.viewmodel

import androidx.lifecycle.ViewModel
import com.tech.mymedicalshopuser.domain.model.ClientChoiceModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewmodel : ViewModel() {

//    private val _cartItemsList = mutableStateListOf<ClientChoiceModel>()
//    val cartItemsList: SnapshotStateList<ClientChoiceModel> = _cartItemsList

    private val _cartItemsList = MutableStateFlow<ArrayList<ClientChoiceModel>>(arrayListOf())
    val cartItemsList = _cartItemsList.asStateFlow()

    private val _subTotalPrice = MutableStateFlow(0.0f)
    val subTotalPrice = _subTotalPrice.asStateFlow()

    fun addItem(product: ClientChoiceModel) {
        _cartItemsList.value = _cartItemsList.value.apply { add(product) }
        getSubTotalPrice() // Update subtotal
    }

    fun removeItem(product: ClientChoiceModel) {
        _cartItemsList.value =
            _cartItemsList.value.filter { it.product_id != product.product_id } as ArrayList<ClientChoiceModel> // Create a new list without the removed item
        getSubTotalPrice() // Update subtotal
    }

    fun clearCart() {
        _cartItemsList.value.clear()
        getSubTotalPrice() // Update subtotal
    }
    fun updateItemCount(clientChoiceModel: ClientChoiceModel, newCount: Int) {

        val cartIndex = _cartItemsList.value.indexOf(clientChoiceModel)
        if (cartIndex != -1 && newCount > 0) {
            // Create a new list with updated item count
            val updatedList = _cartItemsList.value.toMutableList()
            updatedList[cartIndex] = updatedList[cartIndex].copy(product_count = newCount)
            _cartItemsList.value = updatedList.toCollection(ArrayList())
        }
        getSubTotalPrice() // Update subtotal
    }

    private fun getSubTotalPrice(){
        _subTotalPrice.value = cartItemsList.value.sumOf { it.product_price * it.product_count }.toFloat()
    }

    fun findProductById(productId: String): ClientChoiceModel? {
        return _cartItemsList.value.firstOrNull { it.product_id == productId }
    }


}
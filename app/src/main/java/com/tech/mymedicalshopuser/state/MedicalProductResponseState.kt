package com.tech.mymedicalshopuser.state

import com.tech.mymedicalshopuser.data.response.product.ProductModelItem

data class MedicalProductResponseState(
    val isLoading : Boolean = false,
    val data : ArrayList<ProductModelItem>? = null,
    val error : String ?= null
)

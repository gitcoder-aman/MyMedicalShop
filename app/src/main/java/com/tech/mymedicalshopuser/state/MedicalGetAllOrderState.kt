package com.tech.mymedicalshopuser.state

import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import retrofit2.Response

data class MedicalGetAllOrderState(
    val loading : Boolean = false,
    val data : Response<ArrayList<MedicalOrderResponseItem>>? = null,
    val error : String ?= null
)

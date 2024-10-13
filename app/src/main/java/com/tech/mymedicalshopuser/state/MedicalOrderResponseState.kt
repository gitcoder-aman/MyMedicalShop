package com.tech.mymedicalshopuser.state

import com.tech.mymedicalshopuser.data.response.response_status.ResponseStatus

data class MedicalOrderResponseState(
    val isLoading : Boolean = false,
    val data : ResponseStatus? = null,
    val error : String ?= null
)

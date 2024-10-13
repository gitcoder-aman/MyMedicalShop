package com.tech.mymedicalshopuser.state

import com.tech.mymedicalshopuser.data.response.user.GetAllUsersResponseItem

data class MedicalGetAllUserResponseState(
    val isLoading : Boolean = false,
    val data : ArrayList<GetAllUsersResponseItem>? = null,
    val error : String ?= null
)
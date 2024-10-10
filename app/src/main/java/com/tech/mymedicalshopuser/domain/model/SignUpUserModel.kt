package com.tech.mymedicalshopuser.domain.model

data class SignUpUserModel(
    val name : String,
    val phone_info : String,
    val email : String,
    val password : String,
    val address : String,
    val phone_number : String,
    val pinCode : String
)

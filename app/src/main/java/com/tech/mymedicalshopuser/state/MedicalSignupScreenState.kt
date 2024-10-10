package com.tech.mymedicalshopuser.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class MedicalSignupScreenState(
    val userName : MutableState<String> = mutableStateOf(""),
    val mobileNo : MutableState<String> = mutableStateOf(""),
    val email : MutableState<String> = mutableStateOf(""),
    val password : MutableState<String> = mutableStateOf(""),
    val address : MutableState<String> = mutableStateOf(""),
    val pinCode : MutableState<String> = mutableStateOf(""),
)
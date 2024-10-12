package com.tech.mymedicalshopuser.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class MedicalSignInScreenState(
    val email : MutableState<String> = mutableStateOf("manoj@gmail.com"),
    val password : MutableState<String> = mutableStateOf("123456")
)
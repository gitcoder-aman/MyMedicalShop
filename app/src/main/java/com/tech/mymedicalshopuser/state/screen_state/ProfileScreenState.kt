package com.tech.mymedicalshopuser.state.screen_state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ProfileScreenState(
    val userName: MutableState<String> = mutableStateOf(""),
    val userEmail: MutableState<String> = mutableStateOf(""),
    val userPhone: MutableState<String> = mutableStateOf(""),
    val pinCode: MutableState<String> = mutableStateOf(""),
    val address: MutableState<String> = mutableStateOf(""),
    val password: MutableState<String> = mutableStateOf(""),
    val dateOfCreationAccount: MutableState<String> = mutableStateOf("")
)

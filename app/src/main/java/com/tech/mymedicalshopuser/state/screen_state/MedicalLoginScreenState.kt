package com.tech.mymedicalshopuser.state.screen_state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class MedicalSignInScreenState(
    val email : MutableState<String> = mutableStateOf(""),
    val password : MutableState<String> = mutableStateOf("")
)
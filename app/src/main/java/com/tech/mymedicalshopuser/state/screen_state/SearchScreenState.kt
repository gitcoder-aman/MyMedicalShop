package com.tech.mymedicalshopuser.state.screen_state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SearchScreenState(
    val searchTextValue: MutableState<String> = mutableStateOf("")
)

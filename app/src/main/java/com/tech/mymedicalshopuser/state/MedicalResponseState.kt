package com.tech.mymedicalshopuser.state

sealed class MedicalResponseState<out T> {
    object Loading : MedicalResponseState<Nothing>()
    data class Success<out T>(val data: T) : MedicalResponseState<T>()
    data class Error(val message: String) : MedicalResponseState<Nothing>()
}
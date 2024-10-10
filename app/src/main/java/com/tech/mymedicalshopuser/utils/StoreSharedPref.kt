package com.tech.mymedicalshopuser.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.compose.runtime.mutableStateOf
import com.tech.mymedicalshopuser.state.MedicalSignInScreenState
import com.tech.mymedicalshopuser.state.MedicalSignupScreenState

class PreferenceManager(private val context: Context) {
    private val sharedPreferencesSignup = context.getSharedPreferences("SignupPrefs", Context.MODE_PRIVATE)
    private val sharedPreferencesLogin = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

    fun saveSignupData(signupScreenState: MedicalSignupScreenState) {
        with(sharedPreferencesSignup.edit()) {
            putString("userName", signupScreenState.userName.value)
            putString("mobileNo", signupScreenState.mobileNo.value)
            putString("email", signupScreenState.email.value)
            putString("password", signupScreenState.password.value)
            putString("address", signupScreenState.address.value)
            putString("pinCode", signupScreenState.pinCode.value)
            apply()
        }
    }

    fun loadSignupData(): MedicalSignupScreenState {
        return MedicalSignupScreenState(
            userName = mutableStateOf(sharedPreferencesSignup.getString("userName", "") ?: ""),
            mobileNo = mutableStateOf(sharedPreferencesSignup.getString("mobileNo", "") ?: ""),
            email = mutableStateOf(sharedPreferencesSignup.getString("email", "") ?: ""),
            password = mutableStateOf(sharedPreferencesSignup.getString("password", "") ?: ""),
            address = mutableStateOf(sharedPreferencesSignup.getString("address", "") ?: ""),
            pinCode = mutableStateOf(sharedPreferencesSignup.getString("pinCode", "") ?: "")
        )
    }
    fun saveLoginData(loginScreenState: MedicalSignInScreenState) {
        with(sharedPreferencesLogin.edit()) {
            putString("email", loginScreenState.email.value)
            putString("password", loginScreenState.password.value)
            apply()
        }
    }

    fun loadLoginData(): MedicalSignInScreenState {
        return MedicalSignInScreenState(
            email = mutableStateOf(sharedPreferencesLogin.getString("email", "") ?: ""),
            password = mutableStateOf(sharedPreferencesLogin.getString("password", "") ?: "")
        )
    }
}

package com.tech.mymedicalshopuser.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.compose.runtime.mutableStateOf
import com.tech.mymedicalshopuser.state.screen_state.MedicalSignInScreenState
import com.tech.mymedicalshopuser.state.screen_state.MedicalSignupScreenState

class PreferenceManager(private val context: Context) {


    private val isLogin = context.getSharedPreferences("isLoggedIn", MODE_PRIVATE)
    private val isApprove = context.getSharedPreferences("isApproved", MODE_PRIVATE)
    private val userName = context.getSharedPreferences("userName", MODE_PRIVATE)
    private val address = context.getSharedPreferences("address", MODE_PRIVATE)
    private val pinCode = context.getSharedPreferences("pinCode", MODE_PRIVATE)
    private val emailId = context.getSharedPreferences("EmailId", MODE_PRIVATE)
    private val mobileNo = context.getSharedPreferences("MobileNo", MODE_PRIVATE)

    private val sharedPreferencesSignup = context.getSharedPreferences("SignupPrefs", MODE_PRIVATE)
    private val sharedPreferencesLogin = context.getSharedPreferences("LoginPrefs", MODE_PRIVATE)

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

    //for already login or not
    fun setLoginUserId(userId: String) {
        with(isLogin.edit()) {
            putString("isLoggedIn", userId)
            apply()
        }
    }

    fun getLoginUserId(): String? {
        return isLogin.getString("isLoggedIn", "")
    }
    fun setApprovedStatus(isApproved: Int) {
        with(isApprove.edit()) {
            putInt("isApproved", isApproved)
            apply()
        }
    }
    fun getApprovedStatus(): Int {
        return isApprove.getInt("isApproved", 0)
    }
    fun setLoginUserName(userName: String) {
        with(this.userName.edit()) {
            putString("userName", userName)
            apply()
        }
    }
    fun getLoginUserName(): String? {
        return this.userName.getString("userName", "")
    }
    fun setLoginAddress(address: String) {
        with(this.address.edit()) {
            putString("address", address)
            apply()
        }
    }
    fun getLoginAddress(): String? {
        return this.address.getString("address", "")
    }
    fun setLoginPinCode(pinCode: String) {
        with(this.pinCode.edit()) {
            putString("pinCode", pinCode)
            apply()
        }
    }
    fun getLoginPinCode(): String? {
        return this.pinCode.getString("pinCode", "")
    }
    fun setLoginEmailId(emailId: String) {
        with(this.emailId.edit()) {
            putString("emailId", emailId)
            apply()
        }
    }
    fun getLoginEmailId(): String? {
        return this.emailId.getString("emailId", "")
    }
    fun setLoginMobileNo(mobileNo: String) {
        with(this.mobileNo.edit()) {
            putString("mobileNo", mobileNo)
            apply()
        }
    }
    fun getLoginMobileNo(): String? {
        return this.mobileNo.getString("mobileNo", "")
    }



}

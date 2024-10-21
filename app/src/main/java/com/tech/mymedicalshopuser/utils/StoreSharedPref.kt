package com.tech.mymedicalshopuser.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

class PreferenceManager(private val context: Context) {

    private val isLogin = context.getSharedPreferences("isLoggedIn", MODE_PRIVATE)
    private val isApprove = context.getSharedPreferences("isApproved", MODE_PRIVATE)
    private val userName = context.getSharedPreferences("userName", MODE_PRIVATE)
    private val emailId = context.getSharedPreferences("EmailId", MODE_PRIVATE)



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
    fun setApprovedStatus(isApproved: Int = 0) {
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

    fun setLoginEmailId(emailId: String) {
        with(this.emailId.edit()) {
            putString("emailId", emailId)
            apply()
        }
    }
    fun getLoginEmailId(): String? {
        return this.emailId.getString("emailId", "")
    }
}

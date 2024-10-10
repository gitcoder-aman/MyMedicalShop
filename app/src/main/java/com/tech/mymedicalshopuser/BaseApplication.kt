package com.tech.mymedicalshopuser

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        // Clear SharedPreferences when the app is in the background
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            clearSharedPreferences()
        }
    }

    private fun clearSharedPreferences() {
        val sharedPreferencesSignup = getSharedPreferences("SignupPrefs", MODE_PRIVATE)
        val sharedPreferencesLogin = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
        sharedPreferencesSignup.edit().clear().apply()
        sharedPreferencesLogin.edit().clear().apply()
    }
}
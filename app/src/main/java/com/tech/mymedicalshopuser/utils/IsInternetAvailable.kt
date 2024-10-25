package com.tech.mymedicalshopuser.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}

@Composable
fun rememberNetworkStatus(context: Context): Boolean {
    val isConnected = remember { mutableStateOf(isInternetAvailable(context)) }

    // Here you can use a Coroutine or a BroadcastReceiver to update the isConnected state
    // For simplicity, you can just check the connection status once when the composable is first called.

    LaunchedEffect(Unit) {
        while (true) {
            isConnected.value = isInternetAvailable(context)
            delay(1000) // Check every second or adjust the delay as needed
        }
    }

    return isConnected.value
}
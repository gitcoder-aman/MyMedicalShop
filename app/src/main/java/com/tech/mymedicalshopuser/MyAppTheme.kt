package com.tech.mymedicalshopuser

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tech.mymedicalshopuser.ui.theme.GreenColor

@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()

    // Change the status bar color here
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = GreenColor, // Your desired color
            darkIcons = true // or false based on your needs
        )
    }

    MaterialTheme {
        // Your existing theme setup
        content()
    }
}
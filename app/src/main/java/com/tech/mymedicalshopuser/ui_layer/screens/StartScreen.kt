package com.tech.mymedicalshopuser.ui_layer.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.tech.mymedicalshopuser.ui_layer.bottomNavigation.NavigationView

@Composable
fun StartScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        var selectedItem by remember {
            mutableIntStateOf(0)
        }
        val context = LocalContext.current
        NavigationView(
            navController,
            selectedItem = selectedItem,
            onSelectedItem = {
                selectedItem = it
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            }
        )
    }
}
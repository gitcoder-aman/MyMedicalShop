package com.tech.mymedicalshopuser.ui_layer.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.tech.mymedicalshopuser.ui.theme.LightGreenColor
import com.tech.mymedicalshopuser.ui.theme.WhiteGreyColor
import com.tech.mymedicalshopuser.ui_layer.bottomNavigation.NavigationView
import com.tech.mymedicalshopuser.ui_layer.navigation.HomeScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.ProfileScreenRoute

@Composable
fun SearchScreen(navController: NavHostController) {

    var selectedItem by remember {
        mutableIntStateOf(1)
    }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding()
            .fillMaxSize()
            .background(WhiteGreyColor),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
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
    BackHandler {
        navController.navigate(HomeScreenRoute){
            navController.graph.startDestinationRoute?.let { homeScreen ->
                popUpTo(homeScreen) {
                    saveState = true
                }
                restoreState = true
                launchSingleTop = true
                navController.popBackStack(
                    ProfileScreenRoute,
                    inclusive = true
                ) // Clear back stack and go to Screen1
            }
        }
    }

}
package com.tech.mymedicalshopuser.ui_layer.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tech.mymedicalshopuser.ui_layer.screens.SignInScreen
import com.tech.mymedicalshopuser.ui_layer.screens.SignupScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SignInRoute) {

        composable<StartScreen> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            val userLogin = false
            if (userLogin) {
                navController.navigate(HomeScreen)
            } else {
                navController.navigate(SignUpRoute)
            }
        }
        composable<SignInRoute> {
            SignInScreen(navController)
        }
        composable<SignUpRoute> {
            SignupScreen(navController)
        }
        composable<HomeScreen> {

        }

    }

}
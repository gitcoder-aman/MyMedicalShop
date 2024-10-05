package com.tech.mymedicalshopuser.ui_layer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tech.mymedicalshopuser.ui_layer.screens.CartScreen
import com.tech.mymedicalshopuser.ui_layer.screens.HomeScreen
import com.tech.mymedicalshopuser.ui_layer.screens.OrderScreen
import com.tech.mymedicalshopuser.ui_layer.screens.ProfileScreen
import com.tech.mymedicalshopuser.ui_layer.screens.SignInScreen
import com.tech.mymedicalshopuser.ui_layer.screens.SignupScreen
import com.tech.mymedicalshopuser.ui_layer.screens.StartScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = SignInRoute) {

        composable<StartScreenRoute> {
//            Box(modifier = Modifier.fillMaxSize()) {
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//            }
//            val userLogin = false
//            if (userLogin) {
//                navController.navigate(HomeScreenRoute)
//            } else {
//                navController.navigate(SignUpRoute)
//            }
            StartScreen(navController)
        }
        composable<SignInRoute> {
            SignInScreen(navController)
        }
        composable<SignUpRoute> {
            SignupScreen(navController)
        }
        composable<HomeScreenRoute> {
            HomeScreen(navController)
        }
        composable<CartScreenRoute> {
            CartScreen(navController)
        }
        composable<OrderScreenRoute> {
            OrderScreen(navController)
        }
        composable<ProfileScreenRoute> {
            ProfileScreen(navController)
        }

    }

}
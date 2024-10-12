package com.tech.mymedicalshopuser.ui_layer.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tech.mymedicalshopuser.ui_layer.screens.cart_screen.CartScreen
import com.tech.mymedicalshopuser.ui_layer.screens.HomeScreen
import com.tech.mymedicalshopuser.ui_layer.screens.OrderScreen
import com.tech.mymedicalshopuser.ui_layer.screens.ProfileScreen
import com.tech.mymedicalshopuser.ui_layer.screens.SignInScreen
import com.tech.mymedicalshopuser.ui_layer.screens.SignupScreen
import com.tech.mymedicalshopuser.ui_layer.screens.VerificationPendingScreen
import com.tech.mymedicalshopuser.ui_layer.screens.product_detail.ProductDetailScreen
import com.tech.mymedicalshopuser.viewmodel.MedicalAuthViewmodel

@Composable
fun AppNavigation(navController: NavHostController) {
    val medicalAuthViewmodel: MedicalAuthViewmodel = hiltViewModel()

    Log.d("@@Nav", "AppNavigation: ${medicalAuthViewmodel.preferenceManager.getLoginUserId()}")
    Log.d("@@Nav", "AppNavigation: ${medicalAuthViewmodel.preferenceManager.getApprovedStatus()}")
    NavHost(
        navController = navController,
        startDestination = if (medicalAuthViewmodel.preferenceManager.getLoginUserId()!="" &&
            medicalAuthViewmodel.preferenceManager.getApprovedStatus() == 1)
            HomeScreenRoute
        else if (medicalAuthViewmodel.preferenceManager.getLoginUserId()!="")
            VerificationScreenRoute(medicalAuthViewmodel.preferenceManager.getLoginUserId()!!)
        else SignInRoute
    ) {

        composable<SignInRoute> {
            SignInScreen(navController, medicalAuthViewmodel)
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
        composable<ProductDetailScreenRoute> {
            ProductDetailScreen(navController)
        }
        composable<VerificationScreenRoute> { navBackStackEntry ->
            val userId = navBackStackEntry.toRoute<VerificationScreenRoute>().userId
            VerificationPendingScreen(navController,userId)

        }

    }

}
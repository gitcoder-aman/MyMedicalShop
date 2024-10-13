package com.tech.mymedicalshopuser.ui_layer.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tech.mymedicalshopuser.domain.model.ClientChoiceModel
import com.tech.mymedicalshopuser.ui_layer.screens.cart_screen.CartScreen
import com.tech.mymedicalshopuser.ui_layer.screens.HomeScreen
import com.tech.mymedicalshopuser.ui_layer.screens.order_screen.OrderScreen
import com.tech.mymedicalshopuser.ui_layer.screens.ProfileScreen
import com.tech.mymedicalshopuser.ui_layer.screens.SignInScreen
import com.tech.mymedicalshopuser.ui_layer.screens.SignupScreen
import com.tech.mymedicalshopuser.ui_layer.screens.VerificationPendingScreen
import com.tech.mymedicalshopuser.ui_layer.screens.product_detail.ProductDetailScreen
import com.tech.mymedicalshopuser.viewmodel.CartViewmodel
import com.tech.mymedicalshopuser.viewmodel.MainViewmodel
import com.tech.mymedicalshopuser.viewmodel.MedicalAuthViewmodel
import com.tech.mymedicalshopuser.viewmodel.OrderViewmodel

@Composable
fun AppNavigation(navController: NavHostController) {
    val medicalAuthViewmodel: MedicalAuthViewmodel = hiltViewModel()
    val mainViewmodel: MainViewmodel = hiltViewModel()
    val cartViewmodel : CartViewmodel = hiltViewModel()
    val orderViewmodel : OrderViewmodel = hiltViewModel()

    Log.d("@@Nav", "AppNavigation: ${medicalAuthViewmodel.preferenceManager.getLoginUserId()}")
    Log.d("@@Nav", "AppNavigation: ${medicalAuthViewmodel.preferenceManager.getApprovedStatus()}")
    NavHost(
        navController = navController,
        startDestination = if (medicalAuthViewmodel.preferenceManager.getLoginUserId() != "" &&
            medicalAuthViewmodel.preferenceManager.getApprovedStatus() == 1
        )
            HomeScreenRoute
        else if (medicalAuthViewmodel.preferenceManager.getLoginUserId() != "")
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
            HomeScreen(navController,mainViewmodel,medicalAuthViewmodel)
        }
        composable<CartScreenRoute> {
            CartScreen(navController, cartViewmodel,medicalAuthViewmodel,orderViewmodel)
        }
        composable<OrderScreenRoute> {
            OrderScreen(navController,orderViewmodel,medicalAuthViewmodel)
        }
        composable<ProfileScreenRoute> {
            ProfileScreen(navController)
        }
        composable<ProductDetailScreenRoute> { navBackStackEntry ->
            val productName = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productName
            val productId = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productId
            val productImage = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productImage
            val productPrice = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productPrice
            val productRating = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productRating
            val productStock = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productStock
            val productDescription = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productDescription
            val productPower = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productPower
            val productCategory = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productCategory
            val productExpiryDate = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productExpiryDate

            val productItem = ClientChoiceModel(
                product_name = productName,
                product_image = productImage,
                product_price = productPrice,
                product_rating = productRating,
                product_stock = productStock,
                product_description = productDescription,
                product_power = productPower,
                product_category = productCategory,
                product_expiry_date = productExpiryDate,
                product_id = productId,
            )

            ProductDetailScreen(
                navController,
                cartViewmodel,
                productItem
            )

        }

        composable<VerificationScreenRoute> { navBackStackEntry ->
            val userId = navBackStackEntry.toRoute<VerificationScreenRoute>().userId
            VerificationPendingScreen(navController, userId, mainViewmodel)

        }

    }

}
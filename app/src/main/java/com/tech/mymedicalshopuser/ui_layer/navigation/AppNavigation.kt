package com.tech.mymedicalshopuser.ui_layer.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.domain.model.ClientChoiceModelEntity
import com.tech.mymedicalshopuser.local.viewmodel.RoomAddressViewModel
import com.tech.mymedicalshopuser.local.viewmodel.RoomCartViewModel
import com.tech.mymedicalshopuser.ui_layer.screens.cart_screen.CartScreen
import com.tech.mymedicalshopuser.ui_layer.screens.HomeScreen
import com.tech.mymedicalshopuser.ui_layer.screens.profile_screen.ProfileScreen
import com.tech.mymedicalshopuser.ui_layer.screens.SearchScreen
import com.tech.mymedicalshopuser.ui_layer.screens.SignInScreen
import com.tech.mymedicalshopuser.ui_layer.screens.SignupScreen
import com.tech.mymedicalshopuser.ui_layer.screens.VerificationPendingScreen
import com.tech.mymedicalshopuser.ui_layer.screens.order_screen.AddressScreen
import com.tech.mymedicalshopuser.ui_layer.screens.order_screen.AllOrderScreen
import com.tech.mymedicalshopuser.ui_layer.screens.order_screen.CompletedOrderScreen
import com.tech.mymedicalshopuser.ui_layer.screens.order_screen.CreateOrderScreen
import com.tech.mymedicalshopuser.ui_layer.screens.order_screen.OrderDetailScreen
import com.tech.mymedicalshopuser.ui_layer.screens.product_detail.ProductDetailScreen
import com.tech.mymedicalshopuser.ui_layer.screens.profile_screen.MyAccountScreen
import com.tech.mymedicalshopuser.utils.PreferenceManager
import com.tech.mymedicalshopuser.viewmodel.ProfileViewmodel
import com.tech.mymedicalshopuser.viewmodel.MedicalAuthViewmodel
import com.tech.mymedicalshopuser.viewmodel.OrderViewmodel
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation(navController: NavHostController) {
    val medicalAuthViewmodel: MedicalAuthViewmodel = hiltViewModel()
    val mainViewmodel: ProfileViewmodel = hiltViewModel()
    val orderViewmodel: OrderViewmodel = hiltViewModel()
    val roomAddressViewmodel: RoomAddressViewModel = hiltViewModel()
    val roomCartViewmodel: RoomCartViewModel = hiltViewModel()

    val context = LocalContext.current
    val preferenceManager = PreferenceManager(context)

    Log.d("@@Nav", "AppNavigation: ${preferenceManager.getLoginUserId()}")
    Log.d("@@Nav", "AppNavigation: ${preferenceManager.getApprovedStatus()}")
    NavHost(
        navController = navController,
        startDestination = if (preferenceManager.getLoginUserId() != "" &&
            preferenceManager.getApprovedStatus() == 1
        )
            HomeScreenRoute
        else if (preferenceManager.getLoginUserId() != "" && preferenceManager.getApprovedStatus() == 0)
            VerificationScreenRoute(preferenceManager.getLoginUserId()!!)
        else
            SignInRoute
    ) {

        composable<SignInRoute> {
            SignInScreen(navController, medicalAuthViewmodel)
        }
        composable<SignUpRoute> {
            SignupScreen(navController)
        }
        composable<HomeScreenRoute> {
            HomeScreen(navController, mainViewmodel)
        }
        composable<CartScreenRoute> {
            CartScreen(navController, roomCartViewmodel)
        }
        composable<AllOrderScreenRoute> {
            AllOrderScreen(navController, orderViewmodel)
        }
        composable<ProfileScreenRoute> {
            ProfileScreen(navController, mainViewmodel, preferenceManager)
        }
        composable<SearchScreenRoute> {
            SearchScreen(navController)
        }
        composable<AddressScreenRoute> {
            AddressScreen(navController, roomAddressViewmodel)
        }
        composable<CompletedOrderScreenRoute> {
            CompletedOrderScreen(navController)
        }
        composable<MyAccountScreenRoute> {
            MyAccountScreen(navController, mainViewmodel, preferenceManager)
        }
        composable<OrderDetailScreenRoute> {backStackEntry->
            val jsonFormat = Json { ignoreUnknownKeys = true }

            val orderListJson = backStackEntry.toRoute<OrderDetailScreenRoute>().orderList
            val orderList = orderListJson.let { jsonFormat.decodeFromString<List<MedicalOrderResponseItem>>(it) }
            val orderDataInJson = backStackEntry.toRoute<OrderDetailScreenRoute>().orderData
            val orderData = orderDataInJson.let { jsonFormat.decodeFromString<MedicalOrderResponseItem>(it) }

            OrderDetailScreen(orderData,orderList,navController)
        }
        composable<CreateOrderScreenRoute> { backStackEntry ->
            val orderListInJson = backStackEntry.toRoute<CreateOrderScreenRoute>().cartList
            val cartList =
                orderListInJson.let { Json.decodeFromString<List<ClientChoiceModelEntity>>(it) }
            val subTotalPrice = backStackEntry.toRoute<CreateOrderScreenRoute>().subTotalPrice
            CreateOrderScreen(
                cartList,
                orderViewmodel,
                roomAddressViewmodel,
                subTotalPrice,
                roomCartViewmodel,
                navController
            )
        }
        composable<ProductDetailScreenRoute> { navBackStackEntry ->
            val productName = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productName
            val productId = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productId
            val productImageId =
                navBackStackEntry.toRoute<ProductDetailScreenRoute>().productImageId
            val productPrice = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productPrice
            val productRating = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productRating
            val productStock = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productStock
            val productDescription =
                navBackStackEntry.toRoute<ProductDetailScreenRoute>().productDescription
            val productPower = navBackStackEntry.toRoute<ProductDetailScreenRoute>().productPower
            val productCategory =
                navBackStackEntry.toRoute<ProductDetailScreenRoute>().productCategory
            val productExpiryDate =
                navBackStackEntry.toRoute<ProductDetailScreenRoute>().productExpiryDate

            val productItem = ClientChoiceModelEntity(
                product_name = productName,
                product_image_id = productImageId,
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
                productItem,
                roomCartViewmodel
            )

        }

        composable<VerificationScreenRoute> { navBackStackEntry ->
            val userId = navBackStackEntry.toRoute<VerificationScreenRoute>().userId
            VerificationPendingScreen(navController, userId, mainViewmodel)

        }

    }

}
package com.tech.mymedicalshopuser.ui_layer.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.domain.model.ClientChoiceModelEntity
import com.tech.mymedicalshopuser.local.viewmodel.RoomAddressViewModel
import com.tech.mymedicalshopuser.local.viewmodel.RoomCartViewModel
import com.tech.mymedicalshopuser.ui_layer.screens.cart.CartScreen
import com.tech.mymedicalshopuser.ui_layer.screens.home.HomeScreen
import com.tech.mymedicalshopuser.ui_layer.screens.profile.ProfileScreen
import com.tech.mymedicalshopuser.ui_layer.screens.search.SearchScreen
import com.tech.mymedicalshopuser.ui_layer.screens.auth.SignInScreen
import com.tech.mymedicalshopuser.ui_layer.screens.auth.SignupScreen
import com.tech.mymedicalshopuser.ui_layer.screens.auth.VerificationPendingScreen
import com.tech.mymedicalshopuser.ui_layer.screens.order.AddressScreen
import com.tech.mymedicalshopuser.ui_layer.screens.order.AllOrderScreen
import com.tech.mymedicalshopuser.ui_layer.screens.order.CompletedOrderScreen
import com.tech.mymedicalshopuser.ui_layer.screens.order.CreateOrderScreen
import com.tech.mymedicalshopuser.ui_layer.screens.order.OrderDetailScreen
import com.tech.mymedicalshopuser.ui_layer.screens.product.ProductDetailScreen
import com.tech.mymedicalshopuser.ui_layer.screens.profile.MyAccountScreen
import com.tech.mymedicalshopuser.utils.PreferenceManager
import com.tech.mymedicalshopuser.utils.rememberNetworkStatus
import com.tech.mymedicalshopuser.viewmodel.ProfileViewmodel
import com.tech.mymedicalshopuser.viewmodel.MedicalAuthViewmodel
import com.tech.mymedicalshopuser.viewmodel.OrderViewmodel
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation(navController: NavHostController) {

    val context = LocalContext.current
    val isConnected = rememberNetworkStatus(context)

    when {
        !isConnected -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                // Show your Lottie animation when there is no internet
                val noInternetLottieComposition = rememberLottieComposition(
                    spec = LottieCompositionSpec.RawRes(R.raw.check_internet) // Replace with your Lottie file
                )
                val noInternetProgress = animateLottieCompositionAsState(
                    composition = noInternetLottieComposition.value,
                    iterations = LottieConstants.IterateForever,
                    isPlaying = true
                )
                LottieAnimation(
                    composition = noInternetLottieComposition.value,
                    progress = noInternetProgress.value,
                    modifier = Modifier.size(200.dp) // Adjust size as needed
                )
                Text(text = "No Internet Connection", color = Color.Red) // Add an error message
            }
        }
        isConnected->{
            val medicalAuthViewmodel: MedicalAuthViewmodel = hiltViewModel()
            val profileViewmodel: ProfileViewmodel = hiltViewModel()
            val orderViewmodel: OrderViewmodel = hiltViewModel()
            val roomAddressViewmodel: RoomAddressViewModel = hiltViewModel()
            val roomCartViewmodel: RoomCartViewModel = hiltViewModel()

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
                    HomeScreen(navController, profileViewmodel)
                }
                composable<CartScreenRoute> {
                    CartScreen(navController, roomCartViewmodel)
                }
                composable<AllOrderScreenRoute> {
                    AllOrderScreen(navController, orderViewmodel)
                }
                composable<ProfileScreenRoute> {
                    ProfileScreen(navController, profileViewmodel, preferenceManager)
                }
                composable<SearchScreenRoute> {
                    SearchScreen(navController,profileViewmodel)
                }
                composable<AddressScreenRoute> {
                    AddressScreen(navController, roomAddressViewmodel)
                }
                composable<CompletedOrderScreenRoute> {
                    CompletedOrderScreen(navController)
                }
                composable<MyAccountScreenRoute> {
                    MyAccountScreen(navController, profileViewmodel, preferenceManager)
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
                    VerificationPendingScreen(navController, userId, profileViewmodel)

                }

            }
        }
    }


}
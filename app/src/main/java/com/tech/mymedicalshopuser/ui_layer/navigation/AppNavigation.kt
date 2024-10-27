package com.tech.mymedicalshopuser.ui_layer.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
                    SignInRoute,
                enterTransition = {EnterTransition.None},
                exitTransition = {ExitTransition.None}
            ) {

                composable<SignInRoute>(
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                500, easing = LinearEasing
                            )
                        )+slideIntoContainer(
                            animationSpec = tween(500, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Up
                        )
                    }
                ) {
                    SignInScreen(navController, medicalAuthViewmodel)
                }
                composable<SignUpRoute>(
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                500, easing = LinearEasing
                            )
                        )+slideIntoContainer(
                            animationSpec = tween(500, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Up
                        )
                    }
                ) {
                    SignupScreen(navController)
                }
                composable<HomeScreenRoute>(
                    enterTransition ={
                        fadeIn(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideIntoContainer(
                            animationSpec = tween(400, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                ) {
                    HomeScreen(navController, profileViewmodel)
                }
                composable<CartScreenRoute>(
                    enterTransition ={
                        fadeIn(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideIntoContainer(
                            animationSpec = tween(400, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                ) {
                    CartScreen(navController, roomCartViewmodel)
                }
                composable<AllOrderScreenRoute>(
                    enterTransition ={
                        fadeIn(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideIntoContainer(
                            animationSpec = tween(400, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideOutOfContainer(
                            animationSpec = tween(400, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                ) {
                    AllOrderScreen(navController, orderViewmodel)
                }
                composable<ProfileScreenRoute>(
                    enterTransition ={
                        fadeIn(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideIntoContainer(
                            animationSpec = tween(400, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    }
                ) {
                    ProfileScreen(navController, profileViewmodel, preferenceManager)
                }
                composable<SearchScreenRoute>(
                    enterTransition ={
                        fadeIn(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideIntoContainer(
                            animationSpec = tween(400, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    }
                ) {
                    SearchScreen(navController,profileViewmodel)
                }
                composable<AddressScreenRoute>(
                    enterTransition ={
                        fadeIn(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideIntoContainer(
                            animationSpec = tween(400, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    }, exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideOutOfContainer(
                            animationSpec = tween(400, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                ) {
                    AddressScreen(navController, roomAddressViewmodel)
                }
                composable<CompletedOrderScreenRoute>(
                    enterTransition ={
                        fadeIn(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideIntoContainer(
                            animationSpec = tween(400, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    },
                ) {
                    CompletedOrderScreen(navController)
                }
                composable<MyAccountScreenRoute>(
                    enterTransition ={
                        fadeIn(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideIntoContainer(
                            animationSpec = tween(400, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideOutOfContainer(
                            animationSpec = tween(400, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                ) {
                    MyAccountScreen(navController, profileViewmodel, preferenceManager)
                }
                composable<OrderDetailScreenRoute>(
                    enterTransition ={
                        fadeIn(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideIntoContainer(
                            animationSpec = tween(400, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    }, exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideOutOfContainer(
                            animationSpec = tween(400, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                ) {backStackEntry->
                    val jsonFormat = Json { ignoreUnknownKeys = true }

                    val orderListJson = backStackEntry.toRoute<OrderDetailScreenRoute>().orderList
                    val orderList = orderListJson.let { jsonFormat.decodeFromString<List<MedicalOrderResponseItem>>(it) }
                    val orderDataInJson = backStackEntry.toRoute<OrderDetailScreenRoute>().orderData
                    val orderData = orderDataInJson.let { jsonFormat.decodeFromString<MedicalOrderResponseItem>(it) }

                    OrderDetailScreen(orderData,orderList,navController)
                }
                composable<CreateOrderScreenRoute>(
                    enterTransition ={
                        fadeIn(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideIntoContainer(
                            animationSpec = tween(400, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideOutOfContainer(
                            animationSpec = tween(400, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                ) { backStackEntry ->
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
                composable<ProductDetailScreenRoute>(
                    enterTransition ={
                        fadeIn(
                            animationSpec = tween(
                                400, easing = LinearEasing
                            )
                        )+slideIntoContainer(
                            animationSpec = tween(400, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Up
                        )
                    }
                ) { navBackStackEntry ->
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

                composable<VerificationScreenRoute>(
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                500, easing = LinearEasing
                            )
                        )+slideIntoContainer(
                            animationSpec = tween(500, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Up
                        )
                    }
                ) { navBackStackEntry ->
                    val userId = navBackStackEntry.toRoute<VerificationScreenRoute>().userId
                    VerificationPendingScreen(navController, userId, profileViewmodel)

                }

            }
        }
    }


}
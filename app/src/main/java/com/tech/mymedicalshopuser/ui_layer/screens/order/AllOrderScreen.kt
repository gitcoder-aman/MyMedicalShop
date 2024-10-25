package com.tech.mymedicalshopuser.ui_layer.screens.order

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui_layer.navigation.OrderDetailScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.ProfileScreenRoute
import com.tech.mymedicalshopuser.ui_layer.screens.order.component.OrderCardView
import com.tech.mymedicalshopuser.ui_layer.shimmer.ShimmerEffectForSearch
import com.tech.mymedicalshopuser.utils.PreferenceManager
import com.tech.mymedicalshopuser.viewmodel.OrderViewmodel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun AllOrderScreen(
    navController: NavHostController,
    orderViewmodel: OrderViewmodel
) {
    val context = LocalContext.current
    val getAllUserOrderResponseState = orderViewmodel.getAllUserOrders.collectAsState()
    val preferenceManager = PreferenceManager(context)


    // Use a state variable to store the order list
    val getAllUserOrderList = remember { mutableStateListOf<MedicalOrderResponseItem>() }

    // Fetch all user orders when the composable is first composed
    LaunchedEffect(getAllUserOrderResponseState.value) {
        orderViewmodel.getAllUserOrders(preferenceManager.getLoginUserId()!!)
    }

    // Update the order list when data changes
    LaunchedEffect(getAllUserOrderResponseState.value) {
        if (getAllUserOrderResponseState.value.data != null) {
            val response = getAllUserOrderResponseState.value.data
            if (response?.isSuccessful!!) {
                getAllUserOrderList.clear() // Clear the previous list
                response.body()?.let { orders ->
                    getAllUserOrderList.addAll(orders) // Add new orders
                }
            }
        }
    }
    when {
        getAllUserOrderResponseState.value.loading -> {
        }

        getAllUserOrderResponseState.value.data != null -> {
            if (getAllUserOrderResponseState.value.data!!.body()?.isNotEmpty()!!)
                Log.d(
                    "@getAllOrder",
                    "OrderScreen: ${getAllUserOrderResponseState.value.data!!.body()?.size}"
                )
        }

        getAllUserOrderResponseState.value.error != null -> {
            Log.d("@getAllOrder", "OrderScreen: ${getAllUserOrderResponseState.value.error}")
            Toast.makeText(context, getAllUserOrderResponseState.value.error, Toast.LENGTH_SHORT)
                .show()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(4.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            TopAppBar(headerName = "Your Orders", isCloseIcon = true) {
                navController.navigate(ProfileScreenRoute) {
                    popUpTo(navController.graph.startDestinationId) {
                        // Keep the start destination in the back stack
                        inclusive = true
                    }
                    navController.popBackStack()
                }
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = GreenColor)
            Spacer(Modifier.height(16.dp))
            if (getAllUserOrderList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxSize()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    items(getAllUserOrderList.reversed()) { orderData ->
                        OrderCardView(orderData) {
                            val jsonFormat =
                                Json { ignoreUnknownKeys = true }   //for ignore all integer value
                            val orderDateInJson = jsonFormat.encodeToString(orderData)
                            val orderListJson =
                                jsonFormat.encodeToString(getAllUserOrderList.toList())
                            navController.navigate(
                                OrderDetailScreenRoute(
                                    orderData = orderDateInJson,
                                    orderList = orderListJson
                                )
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
                        Spacer(Modifier.height(4.dp))
                    }
                }
            } else {

                val noOrderLottieComposition = rememberLottieComposition(
                    spec = LottieCompositionSpec.RawRes(R.raw.no_order) // Replace with your Lottie file
                )
                val noOrderProgress = animateLottieCompositionAsState(
                    composition = noOrderLottieComposition.value,
                    iterations = LottieConstants.IterateForever,
                    isPlaying = true
                )
                LottieAnimation(
                    composition = noOrderLottieComposition.value,
                    progress = noOrderProgress.value,
                    modifier = Modifier.size(500.dp) // Adjust size as needed
                )
                Text(text = "No Order Found", style = TextStyle(
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.roboto_bold))
                ))
            }

        }
    }
    BackHandler {
        navController.navigate(ProfileScreenRoute) {
            popUpTo(navController.graph.startDestinationId) {
                // Keep the start destination in the back stack
                inclusive = true
            }
        }
    }
}
package com.tech.mymedicalshopuser.ui_layer.screens.order_screen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.data.response.product.ProductModelItem
import com.tech.mymedicalshopuser.ui_layer.bottomNavigation.NavigationView
import com.tech.mymedicalshopuser.ui_layer.navigation.HomeScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.OrderScreenRoute
import com.tech.mymedicalshopuser.viewmodel.MedicalAuthViewmodel
import com.tech.mymedicalshopuser.viewmodel.OrderViewmodel

@Composable
fun OrderScreen(
    navController: NavHostController,
    orderViewmodel: OrderViewmodel,
    medicalAuthViewmodel: MedicalAuthViewmodel
) {
    var selectedItem by remember {
        mutableIntStateOf(2)
    }
    val context = LocalContext.current
    val getAllUserResponseState = orderViewmodel.getAllUserOrders.collectAsState()
    val createOrderResponse = orderViewmodel.createOrder.collectAsState()

    // Use a state variable to store the order list
    val getAllUserOrderList = remember { mutableStateListOf<MedicalOrderResponseItem>() }

    // Fetch all user orders when the composable is first composed
    LaunchedEffect(getAllUserResponseState.value) {
        orderViewmodel.getAllUserOrders(medicalAuthViewmodel.preferenceManager.getLoginUserId()!!)
    }

    // Update the order list when data changes
    LaunchedEffect(getAllUserResponseState.value) {
        if (getAllUserResponseState.value.data != null) {
            val response = getAllUserResponseState.value.data
            if (response?.isSuccessful!!) {
                getAllUserOrderList.clear() // Clear the previous list
                response.body()?.let { orders ->
                    getAllUserOrderList.addAll(orders) // Add new orders
                }
            }
        }
    }
    when {
        getAllUserResponseState.value.loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading...")
            }
        }

        getAllUserResponseState.value.data != null -> {
            Log.d(
                "@getAllOrder",
                "OrderScreen: ${getAllUserResponseState.value.data!!.body()?.size}"
            )
        }

        getAllUserResponseState.value.error != null -> {
            Log.d("@getAllOrder", "OrderScreen: ${getAllUserResponseState.value.error}")
            Toast.makeText(context, getAllUserResponseState.value.error, Toast.LENGTH_SHORT).show()
        }
    }
    when {
        createOrderResponse.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading...")
            }
        }

        createOrderResponse.value.data != null -> {
            Log.d("@order", "OrderScreen: ${createOrderResponse.value.data!!.message}")
            Log.d("@order", "OrderScreen: ${createOrderResponse.value.data!!.status}")
        }

        createOrderResponse.value.error != null -> {
            Toast.makeText(context, createOrderResponse.value.error, Toast.LENGTH_SHORT).show()
            Log.d("@order", "OrderScreen: ${createOrderResponse.value.error}")
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Your Orders", style = TextStyle(
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.roboto_medium))
                )
            )
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                items(getAllUserOrderList) { orderData ->
                    OrderCardView(orderData)
                }
            }
        }
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
        navController.navigate(HomeScreenRoute) {
            navController.graph.startDestinationRoute?.let { homeScreen ->
                popUpTo(homeScreen) {
                    saveState = true
                }
                restoreState = true
                launchSingleTop = true
                navController.popBackStack(
                    OrderScreenRoute,
                    inclusive = true
                ) // Clear back stack and go to Screen1
            }
        }
    }
}
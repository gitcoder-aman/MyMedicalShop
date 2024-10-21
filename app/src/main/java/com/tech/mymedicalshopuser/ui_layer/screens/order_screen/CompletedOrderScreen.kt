package com.tech.mymedicalshopuser.ui_layer.screens.order_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.ui_layer.component.ButtonComponent
import com.tech.mymedicalshopuser.ui_layer.navigation.HomeScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.AllOrderScreenRoute

@Composable
fun CompletedOrderScreen(navController: NavController) {

    val preloaderLottieComposition = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.order_confirmed
        )
    )

    val preloaderProgress = animateLottieCompositionAsState(
        composition = preloaderLottieComposition.value,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            TopAppBar("Order Status") {
                navController.navigate(HomeScreenRoute) {
                    navController.graph.startDestinationRoute?.let { homeScreen ->
                        popUpTo(homeScreen) {
                            inclusive = true
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            LottieAnimation(
                composition = preloaderLottieComposition.value,
                progress = preloaderProgress.value,
                modifier = Modifier.size(300.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Congratulation, You have Successfully Order your Medicine.",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(Font(R.font.roboto_regular))
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            ButtonComponent(
                text = "Track your Orders"
            ) {
                //here to do on click
                navController.navigate(AllOrderScreenRoute)
            }
        }
    }
}
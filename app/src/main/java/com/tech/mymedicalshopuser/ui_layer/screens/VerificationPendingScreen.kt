package com.tech.mymedicalshopuser.ui_layer.screens

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.state.MedicalResponseState
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui_layer.component.ButtonComponent
import com.tech.mymedicalshopuser.ui_layer.navigation.HomeScreenRoute
import com.tech.mymedicalshopuser.viewmodel.MainViewmodel


@Composable
fun VerificationPendingScreen(
    navController: NavHostController,
    userId: String,
    mainViewmodel: MainViewmodel = hiltViewModel()
) {

    mainViewmodel.getSpecificUser(userId)
    val getSpecificUser by mainViewmodel.getSpecificUser.collectAsState()
    var isApproved by remember {
        mutableStateOf(0)
    }


    when (getSpecificUser) {
        is MedicalResponseState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading...")
            }
        }

        is MedicalResponseState.Success -> {
            val user = (getSpecificUser as MedicalResponseState.Success).data
            val isVerifiedAccount = user.body()?.get(0)?.isApproved!!
            LaunchedEffect(Unit) {
                if(user.body()!!.isNotEmpty()) {
                    isApproved = isVerifiedAccount

                    if(isVerifiedAccount == 1){
                        mainViewmodel.preferenceManager.setApprovedStatus(isVerifiedAccount)
                    }else{
                        mainViewmodel.preferenceManager.setApprovedStatus(isVerifiedAccount)
                    }

                }
            }
            Log.d("@isApproved", "Verification Screen: ${user.body()?.get(0)?.isApproved}")
        }

        is MedicalResponseState.Error -> {
            val response = (getSpecificUser as MedicalResponseState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = response)
            }
        }
    }

    val preloaderLottieComposition = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.pending_lottie
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
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LottieAnimation(
                composition = preloaderLottieComposition.value,
                progress = preloaderProgress.value,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = if (isApproved == 1) "VERIFIED" else "PENDING", style = TextStyle(
                    color = GreenColor,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.roboto_bold))
                ), textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (isApproved == 1) "You have Successfully verified the Account." else "Verification your pending\n Please wait.",
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
                text = if (isApproved == 1) "Go to Home Page" else "Wait"
            ) {
                //here to do on click
                if (isApproved == 1) {
                    navController.navigate(HomeScreenRoute) {
                        navController.popBackStack()
                    }
                }
            }

        }

    }

}
package com.tech.mymedicalshopuser.ui_layer.screens.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavHostController
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.state.MedicalResponseState
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui_layer.common.MulticolorText
import com.tech.mymedicalshopuser.ui_layer.component.ButtonComponent
import com.tech.mymedicalshopuser.ui_layer.component.TextFieldComponent
import com.tech.mymedicalshopuser.ui_layer.navigation.HomeScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.SignUpRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.VerificationScreenRoute
import com.tech.mymedicalshopuser.utils.ISOK
import com.tech.mymedicalshopuser.utils.PreferenceManager
import com.tech.mymedicalshopuser.viewmodel.MedicalAuthViewmodel

@Composable
fun SignInScreen(
    navController: NavHostController,
    medicalAuthViewmodel: MedicalAuthViewmodel
) {
    val context = LocalContext.current
    val loginResponseState by medicalAuthViewmodel.loginResponseState.collectAsState()
    val loginScreenState by medicalAuthViewmodel.loginScreenStateData.collectAsState()
    val preferenceManager = PreferenceManager(context)


    when {
        loginResponseState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading...")
            }
        }

        loginResponseState.data != null -> {
            LaunchedEffect(loginResponseState.data) {
                if (loginResponseState.data!!.status == ISOK && loginResponseState.data!!.message != null) {
                    val userId = loginResponseState.data!!.message

                    preferenceManager.setLoginUserId(userId = userId)
                    preferenceManager.setLoginEmailId(loginScreenState.email.value)
                    medicalAuthViewmodel.resetLoginScreenStateData()
                    medicalAuthViewmodel.clearLoginResponseData()

                    if(preferenceManager.getApprovedStatus() == 1) {
                        navController.navigate(HomeScreenRoute) {
                            navController.popBackStack()
                        }
                    }else{
                        navController.navigate(VerificationScreenRoute(userId)) {
                            navController.popBackStack()
                        }
                    }

                    Toast.makeText(
                        context,
                        "Login Successfully $userId",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show()
                }
            }

            Log.d("@@login", "SignInScreen: ${loginResponseState.data!!.message}")
            Log.d("@@login", "SignInScreen: ${loginResponseState.data!!.status}")
        }

        loginResponseState.error != null -> {
            val response = (loginResponseState as MedicalResponseState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = response)
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .background(GreenColor)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        item {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.doctor_image),
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(GreenColor)
                )
            }
        }
        item {
            Box(
                modifier = Modifier
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Login", style = TextStyle(
                            color = Color.Black,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.roboto_bold))
                        )
                    )

                    Spacer(Modifier.height(8.dp))
                    TextFieldComponent(
                        value = loginScreenState.email.value,
                        onValueChange = {
                            loginScreenState.email.value = it
                        }, placeholder = "Email",
                        leadingIcon = R.drawable.email
                    )
                    Spacer(Modifier.height(8.dp))
                    TextFieldComponent(
                        value = loginScreenState.password.value,
                        onValueChange = {
                            loginScreenState.password.value = it
                        }, placeholder = "Password",
                        leadingIcon = R.drawable.password
                    )
                    Spacer(Modifier.height(16.dp))
                    ButtonComponent(
                        text = "Login"
                    ) {

                        medicalAuthViewmodel.loginUser(
                            email = loginScreenState.email.value,
                            password = loginScreenState.password.value
                        )

                    }

                    Spacer(Modifier.height(16.dp))

                    MulticolorText(
                        firstText = "DON'T HAVE AN ACCOUNT? ",
                        secondText = "SIGN UP"
                    ) {
                        navController.navigate(SignUpRoute) {
                            navController.popBackStack()
                        }

                    }
                }
            }
        }
    }
}
package com.tech.mymedicalshopuser.ui_layer.screens.auth

import android.util.Log
import android.util.Patterns
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui_layer.common.MulticolorText
import com.tech.mymedicalshopuser.ui_layer.component.ButtonComponent
import com.tech.mymedicalshopuser.ui_layer.component.TextFieldComponent
import com.tech.mymedicalshopuser.ui_layer.navigation.SignInRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.VerificationScreenRoute
import com.tech.mymedicalshopuser.utils.ISOK
import com.tech.mymedicalshopuser.utils.PreferenceManager
import com.tech.mymedicalshopuser.viewmodel.MedicalAuthViewmodel

@Composable
fun SignupScreen(
    navController: NavHostController,
    medicalAuthViewmodel: MedicalAuthViewmodel = hiltViewModel()
) {

    val context = LocalContext.current
    val preferenceManager = PreferenceManager(context)
    val signupResponseState by medicalAuthViewmodel.signupResponseState.collectAsState()
    val signupScreenState by medicalAuthViewmodel.signupScreenStateData.collectAsState()

    when {
        signupResponseState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading...")
            }
        }

        signupResponseState.data != null -> {
            LaunchedEffect(Unit) {
                if (signupResponseState.data!!.status == ISOK) {
                val userId = signupResponseState.data!!.message

                    preferenceManager.setLoginUserId(userId)
                    preferenceManager.setLoginEmailId(signupScreenState.email.value)

                    navController.navigate(
                        VerificationScreenRoute(userId)
                    ) {
                        navController.popBackStack()
                    }
                    medicalAuthViewmodel.resetSignupScreenStateData()

                    Toast.makeText(
                        context,
                        "Register User Successfully $userId",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(context, "Failed ${signupResponseState.data!!.message}", Toast.LENGTH_SHORT).show()
                }
            }

            Log.d("@@signup", "SignupScreen: ${signupResponseState.data!!.message}")
            Log.d("@@signup", "SignupScreen: ${signupResponseState.data!!.status}")
        }

        signupResponseState.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = signupResponseState.error!!)
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
                        .height(250.dp)
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
                        text = "Register", style = TextStyle(
                            color = Color.Black,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.roboto_bold))
                        )
                    )

                    Spacer(Modifier.height(16.dp))
                    TextFieldComponent(
                        value = signupScreenState.userName.value,
                        onValueChange = {
                            signupScreenState.userName.value = it
                        }, placeholder = "User Name",
                        leadingIcon = R.drawable.add_user
                    )
                    Spacer(Modifier.height(8.dp))
                    TextFieldComponent(
                        value = signupScreenState.mobileNo.value,
                        onValueChange = {
                            signupScreenState.mobileNo.value = it
                        }, placeholder = "Mobile Number",
                        leadingIcon = R.drawable.smartphone
                    )
                    Spacer(Modifier.height(8.dp))
                    TextFieldComponent(
                        value = signupScreenState.email.value,
                        onValueChange = {
                            signupScreenState.email.value = it
                        }, placeholder = "Email",
                        leadingIcon = R.drawable.email
                    )
                    Spacer(Modifier.height(8.dp))
                    TextFieldComponent(
                        value = signupScreenState.password.value,
                        onValueChange = {
                            signupScreenState.password.value = it
                        }, placeholder = "Password",
                        leadingIcon = R.drawable.password
                    )
                    Spacer(Modifier.height(8.dp))
                    TextFieldComponent(
                        value = signupScreenState.address.value,
                        onValueChange = {
                            signupScreenState.address.value = it
                        }, placeholder = "Address",
                        leadingIcon = R.drawable.address
                    )
                    Spacer(Modifier.height(8.dp))
                    TextFieldComponent(
                        value = signupScreenState.pinCode.value,
                        onValueChange = {
                            signupScreenState.pinCode.value = it
                        }, placeholder = "Pincode",
                        leadingIcon = R.drawable.pincode
                    )

                    Spacer(Modifier.height(16.dp))
                    ButtonComponent(
                        text = "Register"
                    ) {
                        if (Patterns.EMAIL_ADDRESS.matcher(signupScreenState.email.value)
                                .matches()
                        ) {
                            medicalAuthViewmodel.signupUser(
                                name = signupScreenState.userName.value,
                                phoneNumber = signupScreenState.mobileNo.value,
                                email = signupScreenState.email.value,
                                password = signupScreenState.password.value,
                                address = signupScreenState.address.value,
                                pinCode = signupScreenState.pinCode.value
                            )
                        } else {
                            Toast.makeText(context, "Email entered not valid!", Toast.LENGTH_SHORT)
                                .show()
                        }


                    }

                    Spacer(Modifier.height(16.dp))

                    MulticolorText(
                        firstText = "ALREADY HAVE AN ACCOUNT? ",
                        secondText = "SIGN IN"
                    ) {
                        navController.navigate(SignInRoute) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}
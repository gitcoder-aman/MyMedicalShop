package com.tech.mymedicalshopuser.ui_layer.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui_layer.common.MulticolorText
import com.tech.mymedicalshopuser.ui_layer.component.ButtonComponent
import com.tech.mymedicalshopuser.ui_layer.component.TextFieldComponent
import com.tech.mymedicalshopuser.ui_layer.navigation.SignInRoute

@Composable
fun SignupScreen(navController: NavHostController) {

    var userName by remember {
        mutableStateOf("")
    }
    var mobileNo by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }
    var pincode by remember {
        mutableStateOf("")
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
                        value = userName,
                        onValueChange = {
                            userName = it
                        }, placeholder = "User Name",
                        leadingIcon = R.drawable.add_user
                    )
                    Spacer(Modifier.height(8.dp))
                    TextFieldComponent(
                        value = mobileNo,
                        onValueChange = {
                            mobileNo = it
                        }, placeholder = "Mobile Number",
                        leadingIcon = R.drawable.smartphone
                    )
                    Spacer(Modifier.height(8.dp))
                    TextFieldComponent(
                        value = email,
                        onValueChange = {
                            email = it
                        }, placeholder = "Email",
                        leadingIcon = R.drawable.email
                    )
                    Spacer(Modifier.height(8.dp))
                    TextFieldComponent(
                        value = password,
                        onValueChange = {
                            password = it
                        }, placeholder = "Password",
                        leadingIcon = R.drawable.password
                    )
                    Spacer(Modifier.height(8.dp))
                    TextFieldComponent(
                        value = address,
                        onValueChange = {
                            address = it
                        }, placeholder = "Address",
                        leadingIcon = R.drawable.address
                    )
                    Spacer(Modifier.height(8.dp))
                    TextFieldComponent(
                        value = pincode,
                        onValueChange = {
                            pincode = it
                        }, placeholder = "Pincode",
                        leadingIcon = R.drawable.pincode
                    )

                    Spacer(Modifier.height(16.dp))
                    ButtonComponent(
                        text = "Register"
                    ) {
                        navController.navigate(SignInRoute)
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
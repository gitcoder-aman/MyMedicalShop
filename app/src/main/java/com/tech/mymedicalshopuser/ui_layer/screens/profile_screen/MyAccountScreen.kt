package com.tech.mymedicalshopuser.ui_layer.screens.profile_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui.theme.LightGreenColor
import com.tech.mymedicalshopuser.ui.theme.WhiteGreyColor
import com.tech.mymedicalshopuser.ui_layer.screens.order_screen.TopAppBar
import com.tech.mymedicalshopuser.utils.PreferenceManager
import com.tech.mymedicalshopuser.viewmodel.ProfileViewmodel

@Composable
fun MyAccountScreen(
    navController: NavController,
    mainViewmodel: ProfileViewmodel,
    preferenceManager: PreferenceManager
) {

    val profileScreenStateUserData by mainViewmodel.profileScreenStateUserData.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WhiteGreyColor), contentAlignment = Alignment.Center
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(WhiteGreyColor),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopAppBar("Edit Profile") {
                        navController.navigateUp()
                    }
                    Spacer(Modifier.height(16.dp))

                    ImageWithArc()
                    Spacer(Modifier.height(8.dp))
                    OutLinedText(
                        value = profileScreenStateUserData.userName.value,
                        onValueChange = {
                            profileScreenStateUserData.userName.value = it
                        },
                        label = "Name",
                        placeholder = "Enter your Name"
                    )
                    Spacer(Modifier.height(4.dp))
                    OutLinedText(
                        value = profileScreenStateUserData.userEmail.value,
                        onValueChange = {
                            profileScreenStateUserData.userEmail.value = it
                        },
                        label = "Email",
                        placeholder = "Enter your Email"
                    )
                    Spacer(Modifier.height(4.dp))
                    OutLinedText(
                        value = profileScreenStateUserData.userPhone.value,
                        onValueChange = {
                            profileScreenStateUserData.userPhone.value = it
                        },
                        label = "Phone No",
                        placeholder = "Enter your Phone"
                    )
                    Spacer(Modifier.height(4.dp))
                    OutLinedText(
                        value = profileScreenStateUserData.pinCode.value,
                        onValueChange = {
                            profileScreenStateUserData.pinCode.value = it
                        },
                        label = "Pin code",
                        placeholder = "Enter your Pin Code"
                    )
                    Spacer(Modifier.height(4.dp))
                    OutLinedText(
                        value = profileScreenStateUserData.address.value,
                        onValueChange = {
                            profileScreenStateUserData.address.value = it
                        },
                        label = "Address",
                        placeholder = "Enter your Address"
                    )
                    Spacer(Modifier.height(4.dp))
                    OutLinedText(
                        value = profileScreenStateUserData.password.value,
                        onValueChange = {
                            profileScreenStateUserData.password.value = it
                        },
                        label = "Password",
                        placeholder = "Enter your Password"
                    )
                    Spacer(Modifier.height(4.dp))
                    OutLinedText(
                        value = profileScreenStateUserData.dateOfCreationAccount.value,
                        onValueChange = {
                            profileScreenStateUserData.dateOfCreationAccount.value = it
                        },
                        label = "Date of Account Creation",
                        placeholder = "",
                        readOnly = true
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(
                            text = "Cancel",
                            containerColor = LightGreenColor,
                            contentColor = Color.Black,
                            textColor = Color.Black,
                            onClick = {
                                navController.navigateUp()
                            }
                        )
                        TextButton(
                            text = "Save",
                            containerColor = GreenColor,
                            contentColor = Color.White,
                            textColor = Color.White,
                            onClick = {
                                mainViewmodel.updateUserData(preferenceManager.getLoginUserId()!!)
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextButton(
    text: String,
    containerColor: Color,
    contentColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {

    Button(
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = ButtonDefaults.buttonElevation(1.dp),
        border = BorderStroke(
            width = 1.dp,
            color = GreenColor
        )
    ) {
        Text(
            text = text, style = TextStyle(
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily(Font(R.font.roboto_medium))
            )
        )
    }
}

@Composable
fun ImageWithArc() {
    Box(
        modifier = Modifier.size(120.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.linkedin),
            contentDescription = "photo",
            modifier = Modifier.size(120.dp)
        )
        Box(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.BottomEnd)
                .shadow(
                    elevation = 1.dp,
                    shape = CircleShape
                )
                .background(LightGreenColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "edit",
                modifier = Modifier
                    .size(24.dp)
                    .shadow(elevation = 1.dp, shape = CircleShape)
                    .background(GreenColor),
                tint = Color.White,
            )
        }
    }
}

@Composable
fun OutLinedText(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeholder)
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(WhiteGreyColor),
        readOnly = readOnly,
        singleLine = singleLine,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = LightGreenColor,
            unfocusedContainerColor = LightGreenColor,
            focusedIndicatorColor = LightGreenColor,
            unfocusedIndicatorColor = LightGreenColor,
            cursorColor = GreenColor,
            focusedLabelColor = GreenColor,
            unfocusedLabelColor = GreenColor
        ),
        keyboardOptions = KeyboardOptions.Default,
        visualTransformation = VisualTransformation.None,
        interactionSource = remember { MutableInteractionSource() }
    )

}

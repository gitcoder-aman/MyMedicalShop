package com.tech.mymedicalshopuser.ui_layer.screens.profile

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.state.screen_state.ProfileScreenState
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui.theme.LightGreenColor
import com.tech.mymedicalshopuser.ui.theme.WhiteGreyColor
import com.tech.mymedicalshopuser.ui_layer.component.AsyncImageComponent
import com.tech.mymedicalshopuser.ui_layer.screens.order.TopAppBar
import com.tech.mymedicalshopuser.utils.GET_IMG_URL
import com.tech.mymedicalshopuser.utils.PreferenceManager
import com.tech.mymedicalshopuser.utils.getFileFromUri
import com.tech.mymedicalshopuser.viewmodel.ProfileViewmodel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

@Composable
fun MyAccountScreen(
    navController: NavController,
    profileViewmodel: ProfileViewmodel,
    preferenceManager: PreferenceManager
) {

    val profileScreenStateUserData by profileViewmodel.profileScreenStateUserData.collectAsState()
    val context = LocalContext.current
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
                    TopAppBar(headerName = "Edit Profile", isCloseIcon = true) {
                        navController.navigateUp()
                    }
                    HorizontalDivider(thickness = 3.dp, color = LightGreenColor)
                    Spacer(Modifier.height(16.dp))

                    Log.d("@Acc", "MyAccountScreen: ${profileScreenStateUserData.userImage.value}")
                    ImageWithArc(profileScreenStateUserData)
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

                                Log.d(
                                    "@Acc",
                                    "MyAccountScreen UserImage: ${profileScreenStateUserData.userImage.value}"
                                )

                                if (profileScreenStateUserData.userImage.value != null) {
                                    val imageFile = getFileFromUri(
                                        context,
                                        profileScreenStateUserData.userImage.value!!
                                    )
                                    Log.d("@Acc", "MyAccountScreen imagefile: $imageFile")
                                    if (imageFile != null && imageFile.exists()) {
                                        val requestFile =
                                            imageFile.asRequestBody("image/jpg".toMediaTypeOrNull())
                                        val body = MultipartBody.Part.createFormData(
                                            "pic",
                                            imageFile.name,
                                            requestFile
                                        )

                                        profileViewmodel.updateUserData(
                                            loginUserId = preferenceManager.getLoginUserId()!!,
                                            userImageFile = body
                                        )
                                        navController.navigateUp()
                                    } else {
                                        Log.e("@Acc", "Image file does not exist.")
                                        Toast.makeText(
                                            context,
                                            "Selected image file is not valid.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {

                                    val body = MultipartBody.Part.createFormData(
                                        "pic",
                                        "-1",
                                    )
                                    // Update user data without an image
                                    profileViewmodel.updateUserData(
                                        loginUserId = preferenceManager.getLoginUserId()!!,
                                        userImageFile = body // or some indication that no image is being sent
                                    )
                                    navController.navigateUp()
                                }
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
fun ImageWithArc(profileScreenStateUserData: ProfileScreenState) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let {
                profileScreenStateUserData.userImage.value = it
            }
        }
    )
    Box(
        modifier = Modifier
            .size(120.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                launcher.launch("image/*")
            },
    ) {
        //if user has change userImage then show new image
        val model = if (profileScreenStateUserData.userImage.value != null) {
            profileScreenStateUserData.userImage.value
        } else {
            GET_IMG_URL + profileScreenStateUserData.userImageId.value
        }
        Image(
            painter = rememberAsyncImagePainter(model),
            contentDescription = "photo",
            modifier = Modifier
                .size(120.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = CircleShape
                ),
            contentScale = ContentScale.Crop
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

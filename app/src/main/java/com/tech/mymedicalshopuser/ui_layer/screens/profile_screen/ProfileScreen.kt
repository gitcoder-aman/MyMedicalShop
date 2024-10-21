package com.tech.mymedicalshopuser.ui_layer.screens.profile_screen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.domain.model.ClientChoiceModelEntity
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui.theme.LightGreenColor
import com.tech.mymedicalshopuser.ui.theme.ProfileGreenColor
import com.tech.mymedicalshopuser.ui.theme.WhiteGreyColor
import com.tech.mymedicalshopuser.ui_layer.bottomNavigation.NavigationView
import com.tech.mymedicalshopuser.ui_layer.navigation.HomeScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.MyAccountScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.AllOrderScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.CreateOrderScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.ProfileScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.SignInRoute
import com.tech.mymedicalshopuser.ui_layer.screens.order_screen.CreateOrderScreen
import com.tech.mymedicalshopuser.utils.PreferenceManager
import com.tech.mymedicalshopuser.viewmodel.ProfileViewmodel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ProfileScreen(
    navController: NavController,
    mainViewmodel: ProfileViewmodel,
    preferenceManager: PreferenceManager
) {

    val context = LocalContext.current
    var selectedItem by remember {
        mutableIntStateOf(3)
    }
    LaunchedEffect(Unit) {
        mainViewmodel.getSpecificUser(preferenceManager.getLoginUserId()!!)
    }
    val getSpecificUser by mainViewmodel.getSpecificUser.collectAsState()
    val updatedProfileResponseState by mainViewmodel.updateProfileResponseState.collectAsState()
    val profileScreenStateUserData by mainViewmodel.profileScreenStateUserData.collectAsState()

    //get current user data
    when {
        getSpecificUser.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = GreenColor)
            }
        }

        getSpecificUser.data != null -> {
            profileScreenStateUserData.userName.value = getSpecificUser.data!![0].name
            profileScreenStateUserData.userPhone.value = getSpecificUser.data!![0].phone_number
            profileScreenStateUserData.userEmail.value = getSpecificUser.data!![0].email
            profileScreenStateUserData.pinCode.value = getSpecificUser.data!![0].pinCode
            profileScreenStateUserData.password.value = getSpecificUser.data!![0].password
            profileScreenStateUserData.address.value = getSpecificUser.data!![0].address
            profileScreenStateUserData.dateOfCreationAccount.value =
                getSpecificUser.data!![0].date_of_account_creation
        }

        getSpecificUser.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = getSpecificUser.error!!)
            }
        }
    }

    //when user data updated response
    when {
        updatedProfileResponseState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = GreenColor)
            }
        }

        updatedProfileResponseState.data != null -> {
            LaunchedEffect(updatedProfileResponseState.data!!.status) {
                if (updatedProfileResponseState.data!!.status == 200) {
                    mainViewmodel.resetProfileScreenStateData()
                    Toast.makeText(
                        context,
                        updatedProfileResponseState.data!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        updatedProfileResponseState.error != null -> {
            Toast.makeText(context, updatedProfileResponseState.error, Toast.LENGTH_SHORT).show()
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
                    ProfileScreenRoute,
                    inclusive = true
                ) // Clear back stack and go to Screen1
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Text(
                    text = "Profile", style = TextStyle(
                        color = Color.Black,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.roboto_medium))
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                PersonalDetail(
                    userName = profileScreenStateUserData.userName.value,
                    userImage = ""
                ) {
                    navController.navigate(MyAccountScreenRoute)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Orders", style = TextStyle(
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.roboto_regular))
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OrderSection(
                    icon = R.drawable.ic_all_orders,
                    title = "All Orders",
                    backgroundColor = Color.Blue
                ) {
                    navController.navigate(AllOrderScreenRoute)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OrderSection(
                    icon = R.drawable.ic_billing,
                    title = "Billing",
                    backgroundColor = Color.Green
                ) {
                    navController.navigate(CreateOrderScreenRoute(
                        cartList = Json.encodeToString(listOf<ClientChoiceModelEntity>()),
                        subTotalPrice = 0.0f
                    ))
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Logout", style = TextStyle(
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.roboto_regular))
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OrderSection(
                    icon = R.drawable.ic_logout,
                    title = "Logout",
                    backgroundColor = Color.Red
                ) {
                    ///here to logout
                    preferenceManager.setLoginUserId("")
                    Log.d("@@Nav", "Profile: ${preferenceManager.getLoginUserId()}")
                    navController.navigate(SignInRoute){
                        navController.graph.startDestinationRoute?.let { signInScreen ->
                            popUpTo(signInScreen) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }
                }

            }
        }


        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(R.drawable.linkedin),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Connect with developer", style = TextStyle(
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            fontFamily = FontFamily(Font(R.font.roboto_regular))
                        )
                    )
                }
                Spacer(modifier = Modifier.height(45.dp))

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
    }
}

//@Preview
@Composable
fun PersonalDetail(
    userName : String,
    userImage : String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .background(WhiteGreyColor)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(R.drawable.medicine),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color.Black,
                    ambientColor = Color.Black,
                    shape = CircleShape
                )
                .background(Color.Black)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(.2f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = userName, style = TextStyle(
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.roboto_medium))
                )
            )
            Text(
                text = "edit personal details", style = TextStyle(
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily(Font(R.font.roboto_regular))
                )
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
    }
}

@Composable
fun OrderSection(
    @DrawableRes icon: Int,
    title: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .background(WhiteGreyColor)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .shadow(
                    elevation = 2.dp,
                    shape = CircleShape
                )
                .background(backgroundColor)
                .padding(8.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title, style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
            ), modifier = Modifier.weight(2f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
    }
}
package com.tech.mymedicalshopuser.ui_layer.bottomNavigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui.theme.LightGreenColor
import com.tech.mymedicalshopuser.ui_layer.navigation.CartScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.HomeScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.OrderScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.ProfileScreenRoute

@Composable
fun NavigationView(
    navController: NavHostController, selectedItem: Int, onSelectedItem: (index: Int) -> Unit
) {

    val items = listOf("Home", "Cart", "Order", "Profile")

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        BottomNavigation(
            modifier = Modifier
                .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
                .clip(
                    RoundedCornerShape(50.dp)
                ),
            backgroundColor = GreenColor
        ) {
            items.forEachIndexed { index, s ->
                BottomNavigationItem(
                    selected = selectedItem == index,
                    onClick = {
                        onSelectedItem(index)
                        when(index){
                            0 -> {
                                navController.navigate(HomeScreenRoute){
                                    navController.popBackStack()
                                }
                            }
                            1->{
                                navController.navigate(CartScreenRoute){
                                    navController.popBackStack()
                                }
                            }
                            2->{
                                navController.navigate(OrderScreenRoute){
                                    navController.popBackStack()
                                }
                            }
                            3->{
                                navController.navigate(ProfileScreenRoute){
                                    navController.popBackStack()
                                }
                            }
                        }
                    },
                    icon = {
                        when (index) {
                            0 -> {
                                Icon(
                                    Icons.Outlined.Home,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(
                                        if (selectedItem == index) {
                                            40.dp
                                        } else {
                                            24.dp
                                        }
                                    )
                                )
                            }

                            1 -> {
                                Icon(
                                    Icons.Outlined.ShoppingCart,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(
                                        if (selectedItem == index) {
                                            40.dp
                                        } else {
                                            24.dp
                                        }
                                    )
                                )
                            }

                            2 -> {
                                Icon(
                                    painter = painterResource(R.drawable.shopping_bag),
                                    contentDescription = null, tint = Color.White,
                                    modifier = Modifier.size(
                                        if (selectedItem == index) {
                                            40.dp
                                        } else {
                                            24.dp
                                        }
                                    )
                                )
                            }

                            3 -> {
                                Icon(
                                    Icons.Outlined.Person,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(
                                        if (selectedItem == index) {
                                            40.dp
                                        } else {
                                            24.dp
                                        }
                                    )
                                )
                            }
                        }
                    }, label = {
                        Text(
                            text = items[index], style = TextStyle(
                                color = Color.White,
                                fontSize = if (selectedItem == index) {
                                    14.sp
                                } else {
                                    12.sp
                                },
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontWeight = if (selectedItem == index) {
                                    androidx.compose.ui.text.font.FontWeight.Bold
                                } else {
                                    androidx.compose.ui.text.font.FontWeight.Normal
                                },
                                letterSpacing = 0.sp
                            )
                        )
                    },
                    selectedContentColor = LightGreenColor,
                    unselectedContentColor = Color.White
                )
            }
        }
    }

}

private fun back(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
            navController.popBackStack(
                route,
                inclusive = true
            ) // Clear back stack and go to Screen1
        }
    }
}
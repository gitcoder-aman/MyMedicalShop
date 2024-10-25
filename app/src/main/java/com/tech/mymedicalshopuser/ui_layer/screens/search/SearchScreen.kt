package com.tech.mymedicalshopuser.ui_layer.screens.search

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.data.response.product.ProductModelItem
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui.theme.WhiteGreyColor
import com.tech.mymedicalshopuser.ui_layer.bottomNavigation.NavigationView
import com.tech.mymedicalshopuser.ui_layer.component.TextFieldComponent
import com.tech.mymedicalshopuser.ui_layer.navigation.HomeScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.ProductDetailScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.ProfileScreenRoute
import com.tech.mymedicalshopuser.ui_layer.screens.search.component.SearchItemView
import com.tech.mymedicalshopuser.ui_layer.shimmer.ShimmerEffectForSearch
import com.tech.mymedicalshopuser.viewmodel.ProfileViewmodel

@Composable
fun SearchScreen(navController: NavHostController, profileViewmodel: ProfileViewmodel) {

    var selectedItem by remember {
        mutableIntStateOf(1)
    }
    var searchText by remember {
        mutableStateOf("")
    }
    //collect all product list
    val getAllProductState = profileViewmodel.getAllProducts.collectAsState()
    val getAllProductList: List<ProductModelItem>

    when {
        getAllProductState.value.isLoading -> {
            ShimmerEffectForSearch(modifier = Modifier.fillMaxSize())
        }

        getAllProductState.value.data != null -> {
            getAllProductList = getAllProductState.value.data!!
            Log.d("@@product", "HomeScreen: ${getAllProductState.value.data!![0].product_name}")

            val filterProductList = getAllProductList.filter {
                it.product_name.lowercase()
                    .contains(searchText.lowercase()) || it.product_category.lowercase()
                    .contains(searchText.lowercase())
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WhiteGreyColor),
                contentAlignment = Alignment.BottomEnd
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    SearchTopBar(
                        searchText = searchText,
                        onValueChange = {
                            searchText = it
                        }
                    )
                    Spacer(Modifier.height(8.dp))

                    if (filterProductList.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                                .padding(bottom = 70.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            items(filterProductList) {
                                SearchItemView(it,
                                    onClick = {
                                        navController.navigate(
                                            ProductDetailScreenRoute(
                                                productName = it.product_name,
                                                productImageId = it.product_image_id,
                                                productPrice = it.product_price,
                                                productRating = it.product_rating.toFloat(),
                                                productStock = it.product_stock,
                                                productDescription = it.product_description,
                                                productPower = it.product_power,
                                                productCategory = it.product_category,
                                                productExpiryDate = it.product_expiry_date,
                                                productId = it.product_id
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    } else {
                        val preloaderLottieComposition = rememberLottieComposition(
                            spec = LottieCompositionSpec.RawRes(
                                R.raw.no_search_found
                            )
                        )

                        val preloaderProgress = animateLottieCompositionAsState(
                            composition = preloaderLottieComposition.value,
                            iterations = LottieConstants.IterateForever,
                            isPlaying = true
                        )
                        LottieAnimation(
                            composition = preloaderLottieComposition.value,
                            progress = preloaderProgress.value,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                    NavigationView(
                        navController,
                        selectedItem = selectedItem,
                        onSelectedItem = {
                            selectedItem = it
                        }
                    )
                }
            }
        }

        getAllProductState.value.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = getAllProductState.value.error!!)
                Log.d("@@product", "HomeScreen error: ${getAllProductState.value.data}")
            }
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

}

@Composable
fun SearchTopBar(
    searchText: String,
    onValueChange: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                GreenColor,
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            )
    ) {

        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
                .background(
                    GreenColor
                )
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Search", style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.roboto_bold))
                )
            )
            Spacer(Modifier.height(8.dp))

            TextFieldComponent(
                value = searchText,
                onValueChange = {
                    onValueChange(it)
                },
                placeholder = "Search medicine",
                leadingIcon = R.drawable.search
            )
        }
    }
}
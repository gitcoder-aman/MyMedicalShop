package com.tech.mymedicalshopuser.ui_layer.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.data.response.product.ProductModelItem
import com.tech.mymedicalshopuser.domain.model.categoryList
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui.theme.LightGreenColor
import com.tech.mymedicalshopuser.ui_layer.bottomNavigation.NavigationView
import com.tech.mymedicalshopuser.ui_layer.component.CategoryItem
import com.tech.mymedicalshopuser.ui_layer.component.ClientItemView
import com.tech.mymedicalshopuser.ui_layer.component.PagerSlider
import com.tech.mymedicalshopuser.ui_layer.component.TextFieldComponent
import com.tech.mymedicalshopuser.ui_layer.navigation.ProductDetailScreenRoute
import com.tech.mymedicalshopuser.viewmodel.MainViewmodel
import com.tech.mymedicalshopuser.viewmodel.MedicalAuthViewmodel

@Composable
fun HomeScreen(
    navController: NavHostController,
    mainViewmodel: MainViewmodel,
    medicalAuthViewmodel: MedicalAuthViewmodel
) {

    var selectedItem by remember {
        mutableIntStateOf(0)
    }
    val context = LocalContext.current

    mainViewmodel.getSpecificUser(medicalAuthViewmodel.preferenceManager.getLoginUserId().toString())
    val getSpecificUser by mainViewmodel.getSpecificUser.collectAsState()

    var isApproved by remember {
        mutableStateOf(0)
    }


    when {
        getSpecificUser.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading...")
            }
        }

        getSpecificUser.data != null -> {
            Log.d("@@verify", "HomeScreen: ${getSpecificUser.data}")
            val isVerifiedAccount = getSpecificUser.data!![0].isApproved
            LaunchedEffect(isVerifiedAccount) {
                isApproved = isVerifiedAccount

                mainViewmodel.preferenceManager.setApprovedStatus(isVerifiedAccount)

            }
            Log.d("@isApproved", "Home Screen: ${getSpecificUser.data!![0].isApproved}")
            Log.d("@isApproved", "Home Screen gerRef: ${mainViewmodel.preferenceManager.getApprovedStatus()}")
        }

        getSpecificUser.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = getSpecificUser.error!!)
            }
        }
    }

    //collect all product list
    val getAllProductState = mainViewmodel.getAllProducts.collectAsState()
    var getAllProductList  = listOf<ProductModelItem>()

    when{
        getAllProductState.value.isLoading->{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading...")
            }
        }
        getAllProductState.value.data != null->{
            getAllProductList = getAllProductState.value.data!!
            Log.d("@@product", "HomeScreen: ${getAllProductState.value.data!!.get(0).product_name}")
        }
        getAllProductState.value.error != null->{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = getAllProductState.value.error!!)
                Log.d("@@product", "HomeScreen error: ${getAllProductState.value.data}")
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    HomeTopBar()
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 70.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(Modifier.height(8.dp))
                    PagerSlider()
                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Categories", style = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily(Font(R.font.roboto_medium))
                            )
                        )
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .shadow(shape = CircleShape, elevation = 1.dp)
                                .background(LightGreenColor)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "see more", style = TextStyle(
                                    color = GreenColor,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily(Font(R.font.roboto_medium))
                                )
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(categoryList) {
                            CategoryItem(
                                itemName = it.itemName,
                                itemImage = it.itemImage
                            ){

                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Client's Choice", style = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily(Font(R.font.roboto_medium))
                            )
                        )
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .shadow(shape = CircleShape, elevation = 1.dp)
                                .background(LightGreenColor)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "see more", style = TextStyle(
                                    color = GreenColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily(Font(R.font.roboto_medium))
                                )
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(getAllProductList) {
                            ClientItemView(
                                itemImage = it.product_image,
                                price = it.product_price,
                                rating = it.product_rating.toFloat(),
                                itemName = it.product_name
                            ){
                                navController.navigate(ProductDetailScreenRoute(
                                    productName = it.product_name,
                                    productImage = it.product_image,
                                    productPrice = it.product_price,
                                    productRating = it.product_rating.toFloat(),
                                    productStock = it.product_stock,
                                    productDescription = it.product_description,
                                    productPower = it.product_power,
                                    productCategory = it.product_category,
                                    productExpiryDate = it.product_expiry_date,
                                    productId = it.product_id
                                ))
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Promotion", style = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily(Font(R.font.roboto_medium))
                            )
                        )
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .shadow(shape = CircleShape, elevation = 1.dp)
                                .background(LightGreenColor)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "see more", style = TextStyle(
                                    color = GreenColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily(Font(R.font.roboto_medium))
                                )
                            )
                        }
                    }
                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(getAllProductList) {
                            ClientItemView(
                                itemImage = it.product_image,
                                price = it.product_price,
                                rating = it.product_rating.toFloat(),
                                itemName = it.product_name
                            ){
                                navController.navigate(ProductDetailScreenRoute(
                                  productName = it.product_name,
                                    productImage = it.product_image,
                                    productPrice = it.product_price,
                                    productRating = it.product_rating.toFloat(),
                                    productStock = it.product_stock,
                                    productDescription = it.product_description,
                                    productPower = it.product_power,
                                    productCategory = it.product_category,
                                    productExpiryDate = it.product_expiry_date,
                                    productId = it.product_id
                                ))

                            }
                        }
                    }


                }
            }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
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

@Composable
fun HomeTopBar() {

    var searchText by remember {
        mutableStateOf("")
    }
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Welcome Aman", style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.roboto_bold))
                    )
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(Modifier.height(8.dp))

            TextFieldComponent(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                placeholder = "Search medicine",
                leadingIcon = R.drawable.search
            )
        }
    }
}
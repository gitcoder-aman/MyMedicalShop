package com.tech.mymedicalshopuser.ui_layer.screens.order_screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.local.entity.AddressEntity
import com.tech.mymedicalshopuser.local.viewmodel.RoomAddressViewModel
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui.theme.LightGreenColor

@Composable
fun AddressScreen(navController: NavHostController, roomAddressViewModel: RoomAddressViewModel) {

    val addressScreenState by roomAddressViewModel.getAddressScreenState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            TopAppBar("Address") {
                navController.navigateUp()
            }
            Spacer(Modifier.height(16.dp))
            Divider(thickness = 2.dp, color = LightGreenColor)
            Spacer(Modifier.height(16.dp))


            AddressTextField(
                value = addressScreenState.address.value,
                onValueChange = {
                    addressScreenState.address.value = it
                },
                placeholder = "Address",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            AddressTextField(
                value = addressScreenState.fullName.value,
                onValueChange = {
                    addressScreenState.fullName.value = it
                },
                placeholder = "Full Name",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            AddressTextField(
                value = addressScreenState.street.value,
                onValueChange = {
                    addressScreenState.street.value = it
                },
                placeholder = "Street",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            AddressTextField(
                value = addressScreenState.phoneNo.value,
                onValueChange = {
                    addressScreenState.phoneNo.value = it
                },
                placeholder = "Phone Number",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            AddressTextField(
                value = addressScreenState.city.value,
                onValueChange = {
                    addressScreenState.city.value = it
                },
                placeholder = "City",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AddressTextField(
                    value = addressScreenState.state.value,
                    onValueChange = {
                        addressScreenState.state.value = it
                    },
                    placeholder = "State",
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                AddressTextField(
                    value = addressScreenState.pinCode.value,
                    onValueChange = {
                        addressScreenState.pinCode.value = it
                    },
                    placeholder = "PinCode",
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    roomAddressViewModel.addAddress(
                        AddressEntity(
                            fullName = addressScreenState.fullName.value,
                            address = addressScreenState.address.value,
                            city = addressScreenState.city.value,
                            state = addressScreenState.state.value,
                            street = addressScreenState.street.value,
                            phoneNo = addressScreenState.phoneNo.value,
                            pinCode = addressScreenState.pinCode.value
                        )
                    )
                    roomAddressViewModel.resetAddressState()
                    navController.navigateUp()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = GreenColor,
                    contentColor = Color.Black
                ), shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Save Address",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold))
                )
            }
        }

    }
}


@Composable
fun AddressTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {

    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Gray,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto_medium))
            )
        }, colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Gray,
            focusedLabelColor = GreenColor,
            unfocusedLabelColor = Color.LightGray,
            cursorColor = GreenColor,
            focusedIndicatorColor = GreenColor,
            unfocusedIndicatorColor = Color.LightGray,
            focusedContainerColor = LightGreenColor,
            unfocusedContainerColor = LightGreenColor
        )
    )

}

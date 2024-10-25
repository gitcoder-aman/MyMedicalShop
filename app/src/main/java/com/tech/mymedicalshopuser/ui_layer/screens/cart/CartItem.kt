package com.tech.mymedicalshopuser.ui_layer.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.domain.model.ClientChoiceModelEntity
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui_layer.component.AsyncImageComponent

@Composable
fun CartItem(
    count: Int,
    itemOnClick: () -> Unit,
    onDelete: () -> Unit,
    increaseItem: () -> Unit,
    decreaseItem: () -> Unit,
    productItem: ClientChoiceModelEntity
) {
    Card(
        onClick = {
            itemOnClick()
        },
        modifier = Modifier
            .padding(top = 5.dp, bottom = 5.dp)
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImageComponent(
                imageId = productItem.product_image_id,
                imageSize = 100.dp,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .weight(0.33f)
                    .padding(5.dp)
                    .shadow(elevation = 1.dp, shape = RoundedCornerShape(16.dp))
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
                    .weight(0.33f),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = productItem.product_name, style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = FontFamily(Font(R.font.roboto_bold))
                    ), maxLines = 1
                )
                Text(
                    text = productItem.product_price.toString(), style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        color = GreenColor,
                    ), maxLines = 1
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
                    .weight(0.33f), verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_minus),
                        contentDescription = "minus-button",
                        modifier = Modifier
                            .weight(0.33f)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                decreaseItem()
                            }, tint = Color.Unspecified
                    )
                    Text(
                        text = count.toString(), style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.W200,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            color = GreenColor,
                        ), maxLines = 1
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.icon_plus),
                        contentDescription = "plus-button",
                        modifier = Modifier
                            .weight(0.33f)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                increaseItem()
                            }, tint = Color.Unspecified
                    )
//                        val cartMenu = CartMenu(itemCount = count, itemIndex = itemIndex)
//                        cartList(cartMenu)
                }
                IconButton(
                    onClick = {  //delete functionality data from firebase
                        onDelete()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = "",
                        tint = GreenColor,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
            }
        }
    }
}


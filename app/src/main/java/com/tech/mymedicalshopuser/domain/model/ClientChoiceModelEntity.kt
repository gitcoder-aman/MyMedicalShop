package com.tech.mymedicalshopuser.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "cartList")
data class ClientChoiceModelEntity(
    val product_category: String,
    val product_description: String,
    val product_expiry_date: String,
    @PrimaryKey val product_id: String = "",
    val product_image_id: String,
    val product_name: String,
    val product_power: String,
    val product_price: Int,
    val product_rating: Float,
    val product_stock: Int,
    var product_count: Int = 1 // like product quantity

)

//val clientChoiceList = listOf(
//    ClientChoiceModel(
//        product_category = "Medicine",
//        product_description = "recover body",
//        product_expiry_date = "10/10/2023",
//        product_id = "1",
//        product_image = R.drawable.med_2,
//        product_name = "cetamol",
//        product_power = "500mg",
//        product_price = 20,
//        product_rating = 4.5f,
//        product_stock = 100
//    ),
//    ClientChoiceModel(
//        product_category = "Medicine",
//        product_description = "recover body",
//        product_expiry_date = "10/10/2023",
//        product_id = "2",
//        product_image = R.drawable.med_2,
//        product_name = "Paracetamol",
//        product_power = "500mg",
//        product_price = 20,
//        product_rating = 4.5f,
//        product_stock = 100
//    ),
//    ClientChoiceModel(
//        product_category = "Medicine",
//        product_description = "recover body",
//        product_expiry_date = "10/10/2023",
//        product_id = "3",
//        product_image = R.drawable.med_2,
//        product_name = "Zehar",
//        product_power = "500mg",
//        product_price = 20,
//        product_rating = 4.5f,
//        product_stock = 100
//    ),
//    ClientChoiceModel(
//        product_category = "Medicine",
//        product_description = "recover body",
//        product_expiry_date = "10/10/2023",
//        product_id = "4",
//        product_image = R.drawable.med_2,
//        product_name = "Pet ka goli",
//        product_power = "500mg",
//        product_price = 20,
//        product_rating = 4.5f,
//        product_stock = 100
//    ),
//    ClientChoiceModel(
//        product_category = "Medicine",
//        product_description = "recover body",
//        product_expiry_date = "10/10/2023",
//        product_id = "5",
//        product_image = R.drawable.med_2,
//        product_name = "Sir dard goli",
//        product_power = "500mg",
//        product_price = 20,
//        product_rating = 4.5f,
//        product_stock = 100
//    ),
//    ClientChoiceModel(
//        product_category = "Medicine",
//        product_description = "recover body",
//        product_expiry_date = "10/10/2023",
//        product_id = "6",
//        product_image = R.drawable.med_2,
//        product_name = "kapra Paracetamol",
//        product_power = "500mg",
//        product_price = 20,
//        product_rating = 4.5f,
//        product_stock = 100
//    ),
//    ClientChoiceModel(
//        product_category = "Medicine",
//        product_description = "recover body",
//        product_expiry_date = "10/10/2023",
//        product_id = "7",
//        product_image = R.drawable.med_2,
//        product_name = "Musceles",
//        product_power = "500mg",
//        product_price = 20,
//        product_rating = 4.5f,
//        product_stock = 100
//    ),
//    ClientChoiceModel(
//        product_category = "Medicine",
//        product_description = "recover body",
//        product_expiry_date = "10/10/2023",
//        product_id = "8",
//        product_image = R.drawable.med_2,
//        product_name = "Fever",
//        product_power = "500mg",
//        product_price = 20,
//        product_rating = 4.5f,
//        product_stock = 100
//    ),
//    ClientChoiceModel(
//        product_category = "Medicine",
//        product_description = "recover body",
//        product_expiry_date = "10/10/2023",
//        product_id = "9",
//        product_image = R.drawable.med_2,
//        product_name = "Body pain",
//        product_power = "500mg",
//        product_price = 20,
//        product_rating = 4.5f,
//        product_stock = 100
//    ),
//    ClientChoiceModel(
//        product_category = "Medicine",
//        product_description = "recover body",
//        product_expiry_date = "10/10/2023",
//        product_id = "10",
//        product_image = R.drawable.med_2,
//        product_name = "Paracetamol",
//        product_power = "500mg",
//        product_price = 20,
//        product_rating = 4.5f,
//        product_stock = 100
//    )
//
//
//)
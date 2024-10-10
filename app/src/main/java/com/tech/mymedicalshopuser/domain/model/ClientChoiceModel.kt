package com.tech.mymedicalshopuser.domain.model

import com.tech.mymedicalshopuser.R

data class ClientChoiceModel(
    val itemImage : Int,
    val price : String,
    val rating : String,
    val itemName : String
)

val clientChoiceList = listOf(
    ClientChoiceModel(
        itemImage = R.drawable.med_1,
        price = "$24.0",
        rating = "4.5",
        itemName = "Paracetamol"
    ),
    ClientChoiceModel(
        itemImage = R.drawable.med_2,
        price = "$24.0",
        rating = "4.0",
        itemName = "Paracetamol"
    ),
    ClientChoiceModel(
        itemImage = R.drawable.med_3,
        price = "$24.0",
        rating = "4.2",
        itemName = "Fever"
    ),
    ClientChoiceModel(
        itemImage = R.drawable.med_4,
        price = "$24.0",
        rating = "4.5",
        itemName = "Ether"
    ),
    ClientChoiceModel(
        itemImage = R.drawable.med_5,
        price = "$24.0",
        rating = "4.5",
        itemName = "Metoprolol "
    ),
    ClientChoiceModel(
        itemImage = R.drawable.med_6,
        price = "$24.0",
        rating = "4.1",
        itemName = "Hydrocodone"
    ),
    ClientChoiceModel(
        itemImage = R.drawable.med_7,
        price = "$24.0",
        rating = "4.0",
        itemName = "Penicillin"
    ),
    ClientChoiceModel(
        itemImage = R.drawable.med_8,
        price = "$24.0",
        rating = "4.5",
        itemName = "Insulin"
    ),
    ClientChoiceModel(
        itemImage = R.drawable.med_9,
        price = "$24.0",
        rating = "4.2",
        itemName = "Aspirin"
    ),
    ClientChoiceModel(
        itemImage = R.drawable.med_10,
        price = "$24.0",
        rating = "4.1",
        itemName = "Salvarsan -- The Cure for Lust"
    )


)
package com.tech.mymedicalshopuser.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_table")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true) val id  : Int = 0,
    val fullName : String,
    val address : String,
    val city : String,
    val state : String,
    val street : String,
    val phoneNo : String,
    val pinCode : String
)

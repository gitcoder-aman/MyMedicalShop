package com.tech.mymedicalshopuser.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tech.mymedicalshopuser.domain.model.ClientChoiceModelEntity
import com.tech.mymedicalshopuser.local.dao.AddressDao
import com.tech.mymedicalshopuser.local.dao.CartDao
import com.tech.mymedicalshopuser.local.entity.AddressEntity

@Database(entities = [ClientChoiceModelEntity::class, AddressEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun addressDao() : AddressDao
}
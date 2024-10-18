package com.tech.mymedicalshopuser.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tech.mymedicalshopuser.domain.model.ClientChoiceModelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert
    suspend fun insertCartList(cartList : ClientChoiceModelEntity)

    @Query("SELECT * FROM cartList")
    fun getAllCartList(): Flow<List<ClientChoiceModelEntity>>

    @Query("DELETE FROM cartList")
    suspend fun deleteAllCartList()

    @Query("UPDATE cartList SET product_count = :productQty WHERE product_id = :productId")
    suspend fun updateCartList(productId : String, productQty : Int)

    @Query("DELETE FROM cartList WHERE product_id = :productId")
    suspend fun deleteCartById(productId : String)

    @Query("SELECT * FROM cartList WHERE product_id = :productId")
    suspend fun findProductById(productId: String): ClientChoiceModelEntity?



}
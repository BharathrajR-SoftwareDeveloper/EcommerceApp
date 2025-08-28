package com.example.ecommerceapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao()
interface GroceryOrderDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert (item: GroceryCartItems, name: String, phone: String, address: String)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (item: GroceryOrderItems)

    @Delete
    suspend fun delete(item: GroceryOrderItems)

    @Query("SELECT * FROM grocery_order_items")
    fun getAllGroceryOrderItems(): LiveData<List<GroceryOrderItems>>

}
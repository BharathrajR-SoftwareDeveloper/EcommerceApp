package com.example.ecommerceapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao()
interface GroceryCartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (item: GroceryCartItems)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (item: GroceryItem)

    @Delete
    suspend fun delete(item: GroceryCartItems)

    @Query("SELECT * FROM grocery_cart_items")
    fun getAllGroceryCartItems(): LiveData<List<GroceryCartItems>>

}
package com.example.ecommerceapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao()
interface GroceryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (item: GroceryItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (item: GroceryCartItems)

    @Delete
    suspend fun delete(item: GroceryItem)

    @Query("SELECT * FROM grocery_items")
    fun getAllGroceryItems(): LiveData<List<GroceryItem>>

}
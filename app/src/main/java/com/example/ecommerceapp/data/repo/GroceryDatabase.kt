package com.example.ecommerceapp.data.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecommerceapp.data.local.GroceryCartDao
import com.example.ecommerceapp.data.local.GroceryCartItems
import com.example.ecommerceapp.data.local.GroceryDao
import com.example.ecommerceapp.data.local.GroceryItem
import com.example.ecommerceapp.data.local.GroceryOrderDao
import com.example.ecommerceapp.data.local.GroceryOrderItems

@Database(entities = [GroceryItem::class,GroceryCartItems::class,GroceryOrderItems::class], version = 3)
abstract class GroceryDatabase : RoomDatabase(){
    abstract fun getGroceryDao(): GroceryDao
    abstract fun getGroceryCartDao(): GroceryCartDao
    abstract fun getGroceryOrderDao(): GroceryOrderDao

    companion object{
        @Volatile
        private var instance: GroceryDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                GroceryDatabase::class.java, "Grocery.db").build()

    }

}
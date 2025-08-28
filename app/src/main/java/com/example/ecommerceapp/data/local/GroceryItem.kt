package com.example.ecommerceapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "grocery_items")
data class GroceryItem(

    @ColumnInfo(name = "item_name")
    var itemName: String,
    @ColumnInfo(name = "item_quantity")
    var itemQuantity: String,
    @ColumnInfo(name = "item_price")
    var itemPrice: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? =null
)
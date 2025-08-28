package com.example.ecommerceapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_cart_items")
data class GroceryCartItems(

    @ColumnInfo(name = "card_item_name")
    var cardItemName: String,
    @ColumnInfo(name = "card_item_quantity")
    var cardItemQuantity: String,
    @ColumnInfo(name = "card_item_price")
    var cardItemPrice: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? =null
)
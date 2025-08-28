package com.example.ecommerceapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_order_items")
data class GroceryOrderItems(

    @ColumnInfo(name = "order_item_name")
    var orderItemName: String,
    @ColumnInfo(name = "order_item_quantity")
    var orderItemQuantity: String,
    @ColumnInfo(name = "order_item_price")
    var orderItemPrice: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "phone")
    var phone: String,
    @ColumnInfo(name = "address")
    var address: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
)
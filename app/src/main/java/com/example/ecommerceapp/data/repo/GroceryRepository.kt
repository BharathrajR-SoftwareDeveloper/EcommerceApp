package com.example.ecommerceapp.data.repo

import com.example.ecommerceapp.data.local.GroceryCartItems
import com.example.ecommerceapp.data.local.GroceryItem
import com.example.ecommerceapp.data.local.GroceryOrderItems

class GroceryRepository(private val db: GroceryDatabase) {
    suspend fun insert(item: GroceryItem)=db.getGroceryDao().insert(item)
    suspend fun insert(item: GroceryCartItems){
        val addedItem= GroceryItem(item.cardItemName,item.cardItemQuantity,item.cardItemPrice)
        db.getGroceryDao().insert(addedItem)
        db.getGroceryCartDao().delete(item)
    }
    suspend fun delete(item: GroceryItem)=db.getGroceryDao().delete(item)

    suspend fun addToCart(item: GroceryItem){
        val addedItem=GroceryCartItems(item.itemName,item.itemQuantity,item.itemPrice)
        db.getGroceryCartDao().insert(addedItem)
        db.getGroceryDao().delete(item)
    }
    suspend fun addToOrder(item: GroceryOrderItems) {
        db.getGroceryOrderDao().insert(item)
    }


    suspend fun removeFromCart(item: GroceryCartItems)=db.getGroceryCartDao().delete(item)
    suspend fun removeFromOrder(item: GroceryOrderItems)=db.getGroceryOrderDao().delete(item)


    fun allGroceryItems()=  db.getGroceryDao().getAllGroceryItems()
    fun allGroceryCartItems()=  db.getGroceryCartDao().getAllGroceryCartItems()
    fun allOrderItems()=db.getGroceryOrderDao().getAllGroceryOrderItems()
}
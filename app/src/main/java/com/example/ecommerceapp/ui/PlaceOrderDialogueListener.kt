package com.example.ecommerceapp.ui

import com.example.ecommerceapp.data.local.GroceryCartItems

interface PlaceOrderDialogueListener {
    fun onAddButtononClicked(item: GroceryCartItems,name: String, phone: String, address: String)
}
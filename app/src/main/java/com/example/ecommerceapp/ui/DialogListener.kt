package com.example.ecommerceapp.ui

import com.example.ecommerceapp.data.local.GroceryItem

interface DialogListener {
    fun onAddButtononClicked(item: GroceryItem)
}
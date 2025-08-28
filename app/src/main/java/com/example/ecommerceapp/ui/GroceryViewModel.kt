package com.example.ecommerceapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.local.GroceryCartItems
import com.example.ecommerceapp.data.local.GroceryItem
import com.example.ecommerceapp.data.local.GroceryOrderItems
import com.example.ecommerceapp.data.repo.GroceryRepository
import kotlinx.coroutines.launch

class GroceryViewModel(private val repository: GroceryRepository): ViewModel() {
    fun insert(item: GroceryItem){
        viewModelScope.launch {
            repository.insert(item)
        }
    }
    fun insert(item: GroceryCartItems){
        viewModelScope.launch {
            repository.insert(item)
        }
    }
    fun delete(item: GroceryItem){
        viewModelScope.launch {
            repository.delete(item)
        }
    }
    fun addToCart(item: GroceryItem){
        viewModelScope.launch {
            repository.addToCart(item)
        }
    }
    fun addToOrder(item: GroceryOrderItems) {
        viewModelScope.launch {
            repository.addToOrder(item)
        }
    }
    fun allOrderItems()= repository.allOrderItems()

    fun removeFromCart(item: GroceryCartItems){
        viewModelScope.launch {
            repository.removeFromCart(item)
        }
    }
    fun removeFromOrder(item: GroceryOrderItems){
        viewModelScope.launch {
            repository.removeFromOrder(item)
        }
    }
    fun allGroceryCartItems()= repository.allGroceryCartItems()
    fun allGroceryItems()= repository.allGroceryItems()
}
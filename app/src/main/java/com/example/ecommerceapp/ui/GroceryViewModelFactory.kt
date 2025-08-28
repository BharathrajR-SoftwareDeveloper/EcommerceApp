package com.example.ecommerceapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceapp.data.repo.GroceryRepository

class GroceryViewModelFactory(private val repository: GroceryRepository):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GroceryViewModel(repository) as T
    }
}
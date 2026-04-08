package com.senac.restapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senac.restapi.api.ApiClient
import com.senac.restapi.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {

    val api = ApiClient.productApi

    private val _products = MutableStateFlow<List<Product>>(
        value = emptyList()
    )

    val products : StateFlow<List<Product>> =
        _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadProduct(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.getProducts()
                _products.value = response.products
            } catch (e: Exception){
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
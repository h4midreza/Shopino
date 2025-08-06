package com.example.shopino.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopino.domain.model.ItemsModel
import com.example.shopino.helper.ManagementCart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val managementCart: ManagementCart
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<ItemsModel>>(emptyList())
    val cartItems: StateFlow<List<ItemsModel>> = _cartItems.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice.asStateFlow()

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            val items = managementCart.getListCart()
            _cartItems.value = items
            calculateTotalPrice(items)
        }
    }

    private fun calculateTotalPrice(items: List<ItemsModel>) {
        var sum = 0.0
        items.forEach {
            sum += it.price * it.numberInCart
        }
        _totalPrice.value = sum
    }

    fun addItemToCart(item: ItemsModel) {
        viewModelScope.launch {
            managementCart.addItem(item)
            loadCartItems()
        }
    }

    fun decreaseItemQuantity(item: ItemsModel) {
        viewModelScope.launch {
            managementCart.removeItem(item) // از متد جدید استفاده می کنیم
            loadCartItems()
        }
    }

    fun increaseItemQuantity(item: ItemsModel) {
        viewModelScope.launch {
            // برای افزایش، آیتمی با تعداد 1 به addItem می فرستیم
            managementCart.addItem(item.copy(numberInCart = 1))
            loadCartItems()
        }
    }

    fun removeProductFromCart(productId: String) {
        viewModelScope.launch {
            managementCart.removeProductCompletely(productId)
            loadCartItems()
        }
    }

    fun clearEntireCart() {
        viewModelScope.launch {
            managementCart.clearCart()
            loadCartItems()
        }
    }
}

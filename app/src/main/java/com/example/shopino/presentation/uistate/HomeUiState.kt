package com.example.shopino.presentation.uistate

import com.example.shopino.domain.model.ItemsModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val products: List<ItemsModel> = emptyList(),
    val error: String? = null
)
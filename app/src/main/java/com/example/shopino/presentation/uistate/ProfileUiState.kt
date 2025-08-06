package com.example.shopino.presentation.uistate

data class ProfileUiState(
    val username: String = "حمیدرضا منافی",
    val phoneNumber: String = "09202000000",
    val currentOrders: Int = 2,
    val deliveredOrders: Int = 74,
    val returnedOrders: Int = 11
)

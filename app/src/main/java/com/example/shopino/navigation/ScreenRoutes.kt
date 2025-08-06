package com.example.shopino.navigation

sealed class ScreenRoutes(val route: String) {
    data object Intro : ScreenRoutes("intro_screen")
    data object Main : ScreenRoutes("main_screen")
    data object Profile : ScreenRoutes("profile_screen")
    data object Cart : ScreenRoutes("cart_screen")
    data object Detail : ScreenRoutes("detail_screen")
    data object ListItem : ScreenRoutes("list_items_screen")
    data object Favourite : ScreenRoutes("favourite_screen")
}
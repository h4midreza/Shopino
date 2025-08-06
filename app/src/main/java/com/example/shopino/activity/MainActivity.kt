package com.example.shopino.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shopino.R
import com.example.shopino.navigation.ScreenRoutes
import com.example.shopino.presentation.intro.IntroScreen
import com.example.shopino.presentation.screen.CartScreen
import com.example.shopino.presentation.screen.DetailScreen
import com.example.shopino.presentation.screen.FavouriteScreen
import com.example.shopino.presentation.screen.ListItemScreen
import com.example.shopino.presentation.screen.MainScreen
import com.example.shopino.presentation.screen.ProfileScreen
import com.example.shopino.presentation.viewmodel.CartViewModel
import com.example.shopino.presentation.viewmodel.DetailViewModel
import com.example.shopino.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ScreenRoutes.Intro.route,
                ) {
                    composable(ScreenRoutes.Intro.route) {
                        IntroScreen(
                            onClick = {
                                navController.navigate(ScreenRoutes.Main.route) {
                                    popUpTo(ScreenRoutes.Intro.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                    composable(ScreenRoutes.Main.route) {
                        MainScreen(
                            navController = navController,
                            onNavigateToCart = { navController.navigate(ScreenRoutes.Cart.route) },
                            onNavigateToProfile = { navController.navigate(ScreenRoutes.Profile.route) },
                            onNavigateToFavourite = { navController.navigate(ScreenRoutes.Favourite.route) }
                        )
                    }
                    composable(ScreenRoutes.Profile.route) {
                        ProfileScreen()
                    }
                    composable(ScreenRoutes.Cart.route) {
                        CartScreen(onBackClick = { navController.popBackStack() })
                    }
                    composable(ScreenRoutes.Favourite.route) {
                        FavouriteScreen()
                    }

                    composable(
                        route = ScreenRoutes.Detail.route + "/{${DetailViewModel.ITEM_MODEL_KEY}}",
                        arguments = listOf(
                            navArgument(DetailViewModel.ITEM_MODEL_KEY) {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->
                        val detailViewModel: DetailViewModel = hiltViewModel()
                        val cartViewModel: CartViewModel = hiltViewModel()
                        DetailScreen(
                            detailViewModel = detailViewModel,
                            cartViewModel = cartViewModel,
                            navController = navController
                        )
                    }

                    composable(
                        route = ScreenRoutes.ListItem.route + "/{title}/{id}",
                        arguments = listOf(
                            navArgument("title") { type = NavType.StringType },
                            navArgument("id") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val defaultTitle = stringResource(id = R.string.default_list_item_title)
                        val defaultId = stringResource(id = R.string.default_list_item_id)

                        val title = backStackEntry.arguments?.getString("title") ?: defaultTitle
                        val id = backStackEntry.arguments?.getString("id") ?: defaultId
                        val mainViewModel: MainViewModel = hiltViewModel()

                        ListItemScreen(
                            title = title,
                            id = id,
                            onBackClick = { navController.popBackStack() },
                            viewModel = mainViewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
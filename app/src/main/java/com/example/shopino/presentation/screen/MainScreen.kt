package com.example.shopino.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shopino.R
import com.example.shopino.navigation.ScreenRoutes
import com.example.shopino.presentation.component.main.BannerCarousel
import com.example.shopino.presentation.component.main.BottomMenu
import com.example.shopino.presentation.component.main.CategoryList
import com.example.shopino.presentation.component.main.Header
import com.example.shopino.presentation.component.ListItems
import com.example.shopino.presentation.component.main.SectionTitle
import com.example.shopino.presentation.viewmodel.MainViewModel
import com.example.shopino.presentation.viewmodel.ProductListUiState
import com.example.shopino.presentation.viewmodel.CategoryListUiState

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    onNavigateToCart: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToFavourite: () -> Unit = {},
) {
    val productListState by viewModel.productListState.collectAsState()
    val banners by viewModel.banners.collectAsState()
    val categoryListState by viewModel.categoryListState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadBannersFromMock()
    }

    ConstraintLayout(modifier = Modifier.background(colorResource(id = R.color.white))) {
        val (scrollList, bottomMenu) = createRefs()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_standard))
                .constrainAs(scrollList) {
                    top.linkTo(parent.top)
                    bottom.linkTo(bottomMenu.top)
                }
        ) {
            item {
                Header(
                    username = stringResource(id = R.string.default_username),
                    onSearchClick = { navController.navigate(ScreenRoutes.Search.route) }
                )
            }

            item {
                if (banners.isEmpty()) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.banner_carousel_loading_height)), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    BannerCarousel(banners = banners)
                }
            }
            item {
                SectionTitle(title = stringResource(id = R.string.section_title_categories), actionText = stringResource(id = R.string.action_more))
            }
            item {
                DisplayCategoryList(
                    categoryListState = categoryListState,
                    navController = navController
                )
            }
            item {
                SectionTitle(title = stringResource(id = R.string.section_title_recommendation), actionText = stringResource(id = R.string.action_see_all))
            }
            item {
                DisplayProductList(
                    productListState = productListState,
                    navController = navController
                )
            }
            item {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_bottom_menu_height)))
            }
        }
        BottomMenu(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomMenu) { bottom.linkTo(parent.bottom) },
            onCartClick = onNavigateToCart,
            onProfileClick = onNavigateToProfile,
            onFavouriteClick = onNavigateToFavourite,
        )
    }
}

@Composable
fun DisplayProductList(
    productListState: ProductListUiState,
    navController: NavController
) {
    when {
        productListState.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.product_list_loading_height)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        productListState.error != null -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.product_list_loading_height)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.error_loading_products, productListState.error ?: stringResource(id = R.string.unknown_error)))
            }
        }
        productListState.items.isNotEmpty() -> {
            ListItems(items = productListState.items, navController = navController)
        }
        else -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.product_list_loading_height)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.no_products_found))
            }
        }
    }
}

@Composable
fun DisplayCategoryList(
    categoryListState: CategoryListUiState,
    navController: NavController
) {
    when {
        categoryListState.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.category_list_loading_height)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        categoryListState.error != null -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.category_list_loading_height)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.error_loading_categories, categoryListState.error ?: stringResource(id = R.string.unknown_error)))
            }
        }
        categoryListState.items.isNotEmpty() -> {
            CategoryList(categories = categoryListState.items, navController = navController)
        }
        else -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.category_list_loading_height)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.no_categories_found))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    MainScreen(navController = navController)
}
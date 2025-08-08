package com.example.shopino.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopino.R
import com.example.shopino.presentation.component.ListItemsFullSize
import com.example.shopino.presentation.component.common.StandardTopAppBar
import com.example.shopino.presentation.viewmodel.MainViewModel
import com.example.shopino.presentation.viewmodel.ProductListUiState

@Composable
fun ListItemScreen(
    title: String,
    onBackClick: () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
    id: String,
    navController: NavController
) {
    val screenState by viewModel.filteredProductListState.collectAsState()

    LaunchedEffect(key1 = id) {
        viewModel.fetchProductsByCategory(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardTopAppBar(
            title = title,
            onBackClick = onBackClick
        )
        DisplayFilteredProductList(
            screenState = screenState,
            navController = navController
        )
    }
}

@Composable
fun DisplayFilteredProductList(
    screenState: ProductListUiState,
    navController: NavController
) {
    when {
        screenState.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        screenState.error != null -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.padding_standard)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.error_loading_filtered_products,
                    screenState.error
                ))
            }
        }
        screenState.items.isNotEmpty() -> {
            ListItemsFullSize(items = screenState.items, navController = navController)
        }
        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.padding_standard)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.no_filtered_products_found))
            }
        }
    }
}

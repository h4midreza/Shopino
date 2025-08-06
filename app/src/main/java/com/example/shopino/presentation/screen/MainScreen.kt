package com.example.shopino.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shopino.R
import com.example.shopino.domain.model.CategoryModel
import com.example.shopino.domain.model.ItemsModel
import com.example.shopino.domain.model.SliderModel
import com.example.shopino.model.testRecommended
import com.example.shopino.presentation.component.main.BannerCarousel
import com.example.shopino.presentation.component.main.BottomMenu
import com.example.shopino.presentation.component.main.CategoryList
import com.example.shopino.presentation.component.main.Header
import com.example.shopino.presentation.component.ListItems
import com.example.shopino.presentation.component.main.SectionTitle
import com.example.shopino.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen(
    navController: NavController,
    onNavigateToCart: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToFavourite: () -> Unit = {},
    onNavigateToDetail: () -> Unit = {},
    onNavigateToListItem: () -> Unit = {},
) {
    val viewModel = MainViewModel()
    val banners = remember { mutableStateListOf<SliderModel>() }
    val Categories = remember { mutableStateListOf<CategoryModel>() }
    val Recommended = remember { mutableStateListOf<ItemsModel>() }
    var showBannersLoading by remember { mutableStateOf(true) }
    var showCategoryLoading by remember { mutableStateOf(true) }
    var showRecommendedLoading by remember { mutableStateOf(true) }

    //Banners
    LaunchedEffect(Unit) {
        val testBanners = listOf(
            SliderModel(R.drawable.banner1),
            SliderModel(R.drawable.banner2),
            SliderModel(R.drawable.banner3),
            SliderModel(R.drawable.banner4),
        )
        banners.clear()
        banners.addAll(testBanners)
        showBannersLoading = false
    }

//    LaunchedEffect(Unit) {
//        viewModel.loadBanners()
//        viewModel.banners.observeForever {
//            banners.clear()
//            banners.addAll(it)
//            showBannersLoading = false
//        }
//    }

    //Category
    LaunchedEffect(Unit) {
        val testCategories = listOf(
            CategoryModel("PC", R.drawable.cat1),
            CategoryModel("SmartPhone", R.drawable.cat2),
            CategoryModel("HeadPhone", R.drawable.cat3),
            CategoryModel("Console", R.drawable.cat4),
            CategoryModel("Camera", R.drawable.cat5),
            CategoryModel("SmartWatch", R.drawable.cat6),
        )
        Categories.clear()
        Categories.addAll(testCategories)
        showCategoryLoading = false
    }

//    LaunchedEffect (Unit){
//        viewModel.loadCategory()
//        viewModel.category.observeForever{
//            Categories.clear()
//            Categories.addAll(it)
//            showCategoryLoading = false
//        }
//    }

    //recommended
    LaunchedEffect(Unit) {
        val testRecommended = testRecommended()
        Recommended.clear()
        Recommended.addAll(testRecommended)
        showRecommendedLoading = false
    }

//    LaunchedEffect(Unit) {
//        viewModel.loadRecommended()
//        viewModel.recommended.observeForever {
//            Recommended.clear()
//            Recommended.addAll(it)
//            showRecommendedLoading = false
//        }
//    }


    ConstraintLayout(modifier = Modifier.background(Color.White)) {
        val (scrollList, bottomMenu) = createRefs()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(scrollList) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            item {
                Header(username = "Hamidreza")
            }

            item {
                if (showBannersLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    BannerCarousel(banners = banners)
                }
            }
            item {
                SectionTitle(title = "Categories", actionText = "See All")
            }
            item {
                if (showCategoryLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    CategoryList(categories = Categories, navController = navController)
                }
            }
            item {
                SectionTitle(title = "Recommendation", actionText = "See All")
            }
            item {
                if (showRecommendedLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    ListItems(items = Recommended, navController = navController)
                }
            }
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        BottomMenu(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomMenu) {
                    bottom.linkTo(parent.bottom)
                },
            onCartClick = onNavigateToCart,
            onProfileClick = onNavigateToProfile,
            onFavouriteClick = onNavigateToFavourite,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    MainScreen(
        navController = navController,
        onNavigateToCart = {},
        onNavigateToProfile = {}
    )
}
package com.example.shopino.presentation.component.main

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavController
import com.example.shopino.R
import com.example.shopino.domain.model.CategoryModel

@Composable
fun CategoryList(categories: SnapshotStateList<CategoryModel>, navController: NavController) {
    var selectedIndex by remember { mutableStateOf(-1) }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.category_list_spacing)),
            contentPadding = PaddingValues(
                horizontal = dimensionResource(id = R.dimen.category_list_content_padding_horizontal),
                vertical = dimensionResource(id = R.dimen.category_list_content_padding_vertical)
            )
        ) {
            items(categories.size) { index ->
                val category = categories[index]
                CategoryItem(
                    item = category,
                    isSelected = selectedIndex == index,
                    onItemClick = {
                        selectedIndex = index
                        Handler(Looper.getMainLooper()).postDelayed({
                            navController.navigate("list_items_screen/${category.title}/${category.id}")
                        }, (1000))
                    }
                )
            }
        }
    }
}
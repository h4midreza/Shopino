package com.example.shopino.presentation.component

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.shopino.R
import com.example.shopino.domain.model.ItemsModel
import com.example.shopino.navigation.ScreenRoutes
import com.google.gson.Gson

@Composable
fun ListItems(items: List<ItemsModel>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .height(dimensionResource(R.dimen.list_items_lazy_grid_height))
            .padding(horizontal = dimensionResource(R.dimen.list_items_lazy_grid_padding_horizontal)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.list_items_lazy_grid_spacing_vertical)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.list_items_lazy_grid_spacing_horizontal)),
    ) {
        items(items.size) { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.list_items_inner_row_spacing_horizontal))
            ) {
                RecommendedItem(items, row, navController)
            }
        }
    }
}

@Composable
fun ListItemsFullSize(items: List<ItemsModel>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.list_items_lazy_grid_padding_horizontal)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.list_items_lazy_grid_spacing_vertical)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.list_items_lazy_grid_spacing_horizontal)),
    ) {
        items(items.size) { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.list_items_inner_row_spacing_horizontal))
            ) {
                RecommendedItem(items, row, navController)
            }
        }
    }
}

@Composable
fun RecommendedItem(
    items: List<ItemsModel>,
    pos: Int,
    navController: NavController
) {
    val context = LocalContext.current
    val currentItem = items[pos]

    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.item_recommended_padding_column))
            .height(dimensionResource(R.dimen.item_recommended_height_column))
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = currentItem.imageUrl.ifEmpty { R.drawable.placeholder },
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder)
            ),
            contentDescription = currentItem.title,
            modifier = Modifier
                .width(dimensionResource(R.dimen.item_recommended_image_width))
                .background(
                    color = colorResource(id = R.color.lightGrey),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.item_recommended_image_corner_radius))
                )
                .height(dimensionResource(R.dimen.item_recommended_image_height))
                .padding(dimensionResource(R.dimen.item_recommended_image_padding_internal))
                .clickable {
                    val gson = Gson()
                    val itemJson = gson.toJson(currentItem)
                    navController.navigate(
                        ScreenRoutes.Detail.route + "/${Uri.encode(itemJson)}"
                    )
                },
            contentScale = ContentScale.Crop
        )

        Text(
            text = currentItem.title,
            color = colorResource(id = R.color.black),
            fontSize = dimensionResource(R.dimen.item_recommended_title_font_size).value.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.item_recommended_title_padding_top))
        )
        Row(
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.item_recommended_rating_price_row_padding_top))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = stringResource(R.string.detail_rating_format),
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.item_recommended_star_padding_end))
                )
                Text(
                    text = currentItem.ratingRate.toString(),
                    color = colorResource(id = R.color.black),
                    fontSize = dimensionResource(R.dimen.item_recommended_rating_font_size).value.sp,
                )
            }
            Text(
                text = stringResource(R.string.item_recommended_price_format, currentItem.price),
                color = colorResource(id = R.color.purple),
                fontSize = dimensionResource(R.dimen.item_recommended_price_font_size).value.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun getDrawableIdByName(name: String, context: Context): Int {
    return context.resources.getIdentifier(name, "drawable", context.packageName)
}


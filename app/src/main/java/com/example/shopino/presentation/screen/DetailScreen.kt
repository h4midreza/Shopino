package com.example.shopino.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.shopino.R
import com.example.shopino.navigation.ScreenRoutes
import com.example.shopino.presentation.component.common.StandardTopAppBar
import com.example.shopino.presentation.viewmodel.CartViewModel
import com.example.shopino.presentation.viewmodel.DetailViewModel
import com.example.shopino.presentation.viewmodel.FavouriteViewModel

@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel,
    cartViewModel: CartViewModel = hiltViewModel(),
    navController: NavController
) {
    val itemState by detailViewModel.item.collectAsState()

    if (itemState == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }
    val currentItem = itemState ?: return
    var selectedModelIndex by remember { mutableIntStateOf(0) }

    val navGraphStoreOwner = remember(navController.currentBackStackEntry) {
        navController.getBackStackEntry("app_nav_graph")
    }
    val favouriteViewModel: FavouriteViewModel = hiltViewModel(navGraphStoreOwner)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        StandardTopAppBar(
            title = "",
            onBackClick = { navController.popBackStack() },
            actions = {
                val favouriteItems by favouriteViewModel.favouriteItems.collectAsState()
                val isFavourite = favouriteItems.any { it.id == currentItem.id }

                IconButton(onClick = {
                    if (isFavourite) {
                        favouriteViewModel.removeFavourite(currentItem)
                    } else {
                        favouriteViewModel.addFavourite(currentItem.copy(isFavorite = true))
                    }
                }) {
                    Icon(
                        imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.detail_favorite_button_description),
                        tint = if (isFavourite) Color.Red else Color.Gray
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimensionResource(id = R.dimen.padding_standard))
                .padding(bottom = dimensionResource(id = R.dimen.padding_standard)) // Add bottom padding for scrollable content
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = currentItem.imageUrl.ifEmpty { R.drawable.placeholder },
                ),
                contentDescription = stringResource(id = R.string.detail_product_image_description, currentItem.title),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.detail_main_image_height))
                    .background(
                        colorResource(id = R.color.lightGrey),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_standard))
                    )
                    .padding(dimensionResource(id = R.dimen.detail_main_image_internal_padding))
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.detail_title_price_row_padding_top))
                    .fillMaxWidth()
            ) {
                Text(
                    text = currentItem.title,
                    fontSize = dimensionResource(id = R.dimen.font_size_title_large).value.sp,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .weight(1F)
                        .padding(end = dimensionResource(id = R.dimen.detail_title_padding_end))
                )

                Text(
                    text = stringResource(id = R.string.detail_price_format, currentItem.price),
                    fontSize = dimensionResource(id = R.dimen.font_size_title_medium).value.sp,
                    style = MaterialTheme.typography.headlineSmall,
                    color = colorResource(id = R.color.purple)
                )
            }
            RatingBar(rating = currentItem.ratingRate, ratingCount = currentItem.ratingCount)

            ModelSelector(models = currentItem.modelsAvailable,
                selectedModelIndex = selectedModelIndex,
                onModelSelected = { selectedModelIndex = it })

            Text(
                text = currentItem.description,
                fontSize = dimensionResource(id = R.dimen.font_size_body).value.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray, // TODO: از ریسورس رنگ استفاده کنید
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.detail_description_padding_vertical))
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        cartViewModel.addItemToCart(currentItem.copy(numberInCart = 1))
                    },
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_medium)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.purple)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = dimensionResource(id = R.dimen.detail_add_to_cart_button_padding_end))
                        .height(dimensionResource(id = R.dimen.detail_add_to_cart_button_height))
                ) {
                    Text(
                        text = stringResource(id = R.string.detail_add_to_cart_button),
                        fontSize = dimensionResource(id = R.dimen.font_size_button).value.sp,
                        color = Color.White // TODO: از ریسورس رنگ استفاده کنید
                    )
                }

                IconButton(
                    onClick = {
                        navController.navigate(ScreenRoutes.Cart.route)
                    },
                    modifier = Modifier
                        .background(
                            colorResource(id = R.color.lightGrey),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_medium))
                        )
                        .padding(dimensionResource(id = R.dimen.detail_cart_icon_button_padding))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_2),
                        contentDescription = stringResource(id = R.string.detail_cart_icon_description),
                        tint = colorResource(id = R.color.purple)
                    )
                }
            }
        }
    }
}

@Composable
fun RatingBar(rating: Double, ratingCount: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.detail_rating_bar_padding_top))
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.detail_select_model_title),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.star),
            contentDescription = stringResource(R.string.detail_rating_format),
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.detail_rating_star_padding_end))
        )
        Text(
            text = stringResource(id = R.string.detail_rating_format, rating),
            style = MaterialTheme.typography.bodyMedium // Keep or adjust
        )
        Text(
            text = stringResource(R.string.rating_count_format, ratingCount),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ModelSelector(
    models: List<String>,
    selectedModelIndex: Int,
    onModelSelected: (Int) -> Unit
) {
    if (models.isEmpty()) return

    LazyRow(Modifier.padding(vertical = dimensionResource(id = R.dimen.detail_model_selector_row_padding_vertical))) {
        itemsIndexed(models) { index, model ->
            val isSelected = selectedModelIndex == index
            val borderColor = if (isSelected) colorResource(id = R.color.purple) else Color.Transparent // No border if not selected
            val backgroundColor = if (isSelected) colorResource(id = R.color.lightPurple) else colorResource(id = R.color.lightGrey)
            val textColor = if (isSelected) colorResource(id = R.color.purple) else colorResource(id = R.color.black)

            Box(modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.detail_model_selector_item_padding_end))
                .height(dimensionResource(id = R.dimen.detail_model_selector_item_height))
                .border(
                    width = dimensionResource(id = R.dimen.detail_model_selector_item_border_width),
                    color = borderColor,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_medium))
                )
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_medium))
                )
                .clickable { onModelSelected(index) }
                .padding(horizontal = dimensionResource(id = R.dimen.detail_model_selector_item_internal_padding_horizontal))
            ) {
                Text(
                    text = model,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

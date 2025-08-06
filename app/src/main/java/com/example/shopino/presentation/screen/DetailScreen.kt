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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateOf
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.shopino.R
import com.example.shopino.navigation.ScreenRoutes
import com.example.shopino.presentation.viewmodel.CartViewModel
import com.example.shopino.presentation.viewmodel.DetailViewModel

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

    var selectedImageUrl by remember(currentItem.picUrl) { mutableStateOf(currentItem.picUrl.firstOrNull() ?: "") }
    var selectedModelIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Consider R.color.background_light or similar
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(id = R.dimen.padding_standard))
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.detail_top_bar_padding_top_specific),
                    bottom = dimensionResource(id = R.dimen.detail_top_bar_padding_vertical)
                )
                .fillMaxWidth()
        ) {
            val (back, fav) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = stringResource(id = R.string.detail_back_button_description),
                modifier = Modifier
                    .clickable { navController.popBackStack() }
                    .constrainAs(back) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.fav_icon),
                contentDescription = stringResource(id = R.string.detail_favorite_button_description),
                modifier = Modifier
                    .constrainAs(fav) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }

        Image(
            painter = rememberAsyncImagePainter(
                model = selectedImageUrl.ifEmpty { R.drawable.placeholder },
            ),
            contentDescription = stringResource(id = R.string.detail_product_image_description, currentItem.title),
            modifier = Modifier
                .fillMaxWidth() // Changed from fillMaxSize to fillMaxWidth
                .height(dimensionResource(id = R.dimen.detail_main_image_height))
                .background(
                    colorResource(id = R.color.lightGrey), // Ensure R.color.lightGrey exists
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_standard))
                )
                .padding(dimensionResource(id = R.dimen.detail_main_image_internal_padding))
        )

        if (currentItem.picUrl.size > 1) { // Show thumbnails only if there's more than one image
            LazyRow(
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.detail_thumbnail_row_padding_vertical))
            ) {
                itemsIndexed(currentItem.picUrl) { _, imageUrl ->
                    ImageThumbnail(
                        imageUrl = imageUrl,
                        isSelected = selectedImageUrl == imageUrl,
                        onClick = { selectedImageUrl = imageUrl }
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.detail_title_price_row_padding_top))
                .fillMaxWidth() // Added to ensure price is at the end
        ) {
            Text(
                text = currentItem.title,
                fontSize = dimensionResource(id = R.dimen.font_size_title_large).value.sp,
                style = MaterialTheme.typography.headlineSmall, // Keep if it provides specific styling not covered by fontSize alone
                modifier = Modifier
                    .weight(1F) // Allow title to take available space
                    .padding(end = dimensionResource(id = R.dimen.detail_title_padding_end))
            )

            Text(
                text = stringResource(id = R.string.detail_price_format, currentItem.price),
                fontSize = dimensionResource(id = R.dimen.font_size_title_medium).value.sp,
                style = MaterialTheme.typography.headlineSmall, // Keep or adjust
                color = colorResource(id = R.color.purple) // Ensure R.color.purple exists
            )
        }
        RatingBar(rating = currentItem.rating)

        ModelSelector(models = currentItem.model,
            selectedModelIndex = selectedModelIndex,
            onModelSelected = { selectedModelIndex = it })

        Text(
            text = currentItem.description,
            fontSize = dimensionResource(id = R.dimen.font_size_body).value.sp,
            style = MaterialTheme.typography.bodyMedium, // Keep or adjust
            color = Color.DarkGray, // Consider R.color.text_secondary or similar
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.detail_description_padding_vertical))
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    currentItem?.let { product ->
                        cartViewModel.addItemToCart(product.copy(numberInCart = 1))
                    }
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
                    color = Color.White // Consider R.color.white
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
                    painter = painterResource(id = R.drawable.btn_2), // Ensure R.drawable.btn_2 (cart icon) exists
                    contentDescription = stringResource(id = R.string.detail_cart_icon_description),
                    tint = colorResource(id = R.color.purple)
                )
            }
        }
    }
}

@Composable
fun RatingBar(rating: Double) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.detail_rating_bar_padding_top))
            .fillMaxWidth() // Added to ensure rating text is at the end
    ) {
        Text(
            text = stringResource(id = R.string.detail_select_model_title), // This seems to be the title for "ModelSelector" actually
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f) // Allow text to take available space
        )
        Image(
            painter = painterResource(id = R.drawable.star), // Ensure R.drawable.star exists
            contentDescription = null, // Or provide a description if meaningful
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.detail_rating_star_padding_end))
        )
        Text(
            text = stringResource(id = R.string.detail_rating_format, rating),
            style = MaterialTheme.typography.bodyMedium // Keep or adjust
            // fontSize = dimensionResource(R.dimen.font_size_caption).value.sp // Example if using a specific caption size
        )
    }
}

@Composable
fun ModelSelector(
    models: List<String>,
    selectedModelIndex: Int,
    onModelSelected: (Int) -> Unit
) {
    if (models.isEmpty()) return // Avoid showing LazyRow if there are no models

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
                    // fontSize = dimensionResource(R.dimen.font_size_caption).value.sp // Example
                )
            }
        }
    }
}


@Composable
fun ImageThumbnail(
    imageUrl: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) colorResource(id = R.color.purple) else Color.Transparent
    val backgroundColor = if (isSelected) colorResource(id = R.color.lightPurple) else colorResource(id = R.color.lightGrey)

    Box(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.detail_thumbnail_padding))
            .size(dimensionResource(id = R.dimen.detail_thumbnail_size))
            .border(
                width = dimensionResource(id = R.dimen.detail_thumbnail_border_width),
                color = borderColor,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_medium))
            )
            .background(
                backgroundColor,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_medium))
            )
            .clickable(onClick = onClick)
            .padding(dimensionResource(id = R.dimen.detail_thumbnail_internal_padding)) // Padding for the image inside the border/background
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = imageUrl,
                placeholder = painterResource(R.drawable.placeholder),
            ),
            contentDescription = null, // Or a generic "Product thumbnail"
            modifier = Modifier.fillMaxSize()
        )
    }
}


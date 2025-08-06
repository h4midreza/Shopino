package com.example.shopino.presentation.component.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.shopino.R

@Composable
fun BottomMenu(
    modifier: Modifier,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    onFavouriteClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(
                start = dimensionResource(id = R.dimen.bottom_menu_horizontal_padding),
                end = dimensionResource(id = R.dimen.bottom_menu_horizontal_padding),
                bottom = dimensionResource(id = R.dimen.bottom_menu_bottom_padding)
            )
            .background(
                colorResource(id = R.color.purple),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.bottom_menu_corner_radius))
            ),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        BottomMenuItem(
            icon = Icons.Default.Home,
            text = stringResource(id = R.string.bottom_menu_home)
        )
        BottomMenuItem(
            icon = Icons.Default.ShoppingCart,
            text = stringResource(id = R.string.bottom_menu_cart),
            onItemClick = onCartClick
        )
        BottomMenuItem(
            icon = Icons.Default.Favorite,
            text = stringResource(id = R.string.bottom_menu_favorites),
            onItemClick = onFavouriteClick
        )
        BottomMenuItem(
            icon = Icons.Default.Category,
            text = stringResource(id = R.string.bottom_menu_category)
        )
        BottomMenuItem(
            icon = Icons.Default.Person,
            text = stringResource(id = R.string.bottom_menu_profile),
            onItemClick = onProfileClick
        )
    }
}

@Composable
fun BottomMenuItem(
    icon: ImageVector,
    text: String,
    onItemClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.bottom_menu_item_height))
            .clickable { onItemClick?.invoke() }
            .padding(dimensionResource(id = R.dimen.bottom_menu_item_internal_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = text, tint = Color.White) // tint = Color.White می‌تواند در colors.xml تعریف شود
        Text(
            text = text,
            color = Color.White,
            fontSize = dimensionResource(id = R.dimen.bottom_menu_item_font_size).value.sp // تبدیل Dp به Sp
        )
    }
}
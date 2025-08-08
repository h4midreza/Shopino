package com.example.shopino.presentation.component.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopino.R
import com.example.shopino.domain.model.CategoryModel

@Composable
fun CategoryItem(
    item: CategoryModel,
    isSelected: Boolean,
    onItemClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onItemClick)
            .background(
                color = if (isSelected) colorResource(id = R.color.purple) else colorResource(id = R.color.transparent),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.category_item_corner_radius))
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.iconResId),
            contentDescription = stringResource(R.string.category_icon_description, item.displayName),
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.category_item_image_size))
                .background(
                    color = if (isSelected) colorResource(id = R.color.transparent) else colorResource(id = R.color.lightGrey),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.category_item_corner_radius))
                )
                .padding(4.dp),
            contentScale = ContentScale.Inside,
            colorFilter = if (isSelected) ColorFilter.tint(colorResource(id = R.color.white)) else ColorFilter.tint(colorResource(id = R.color.black))
        )

        if (isSelected) {
            Text(
                text = item.displayName,
                color = colorResource(id = R.color.white),
                fontSize = dimensionResource(id = R.dimen.category_item_font_size).value.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.category_item_text_padding_end))
            )
        }
    }
}

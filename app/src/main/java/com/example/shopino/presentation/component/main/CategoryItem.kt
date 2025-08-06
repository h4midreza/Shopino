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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
                color = (if (isSelected) colorResource(id = R.color.purple) else Color.Transparent),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.category_item_corner_radius))
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.id),
            contentDescription = item.title,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.category_item_image_size))
                .background(
                    color = if (isSelected) Color.Transparent else colorResource(id = R.color.lightGrey),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.category_item_corner_radius))
                ),
            contentScale = ContentScale.Inside,
            colorFilter = if (isSelected) ColorFilter.tint(Color.White) else ColorFilter.tint(Color.Black)
        )

        if (isSelected) {
            Text(
                text = item.title,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.category_item_text_padding_end))
            )
        }
    }
}

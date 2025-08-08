package com.example.shopino.presentation.component.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.shopino.R
import com.example.shopino.domain.model.SliderModel
import com.google.accompanist.pager.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BannerCarousel(banners: List<SliderModel>) {
    val pagerState = remember { PagerState() }
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(count = banners.size, state = pagerState) { page ->
            Image(
                painter = painterResource(id = banners[page].imageResId),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_small),
                        end = dimensionResource(id = R.dimen.padding_small),
                        top = dimensionResource(id = R.dimen.padding_standard),
                        bottom = dimensionResource(id = R.dimen.padding_small)
                    )
                    .height(dimensionResource(id = R.dimen.banner_carousel_height))
            )
        }

        DotIndicator(
            totalDots = banners.size,
            selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
            dotSize = dimensionResource(id = R.dimen.banner_dot_size),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun DotIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = colorResource(id = R.color.purple),
    unSelectedColor: Color = colorResource(id = R.color.grey),
    dotSize: Dp
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalDots) { index ->
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .clip(CircleShape)
                    .background(if (index == selectedIndex) selectedColor else unSelectedColor)
            )
            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.banner_dot_spacing)))
            }
        }
    }
}

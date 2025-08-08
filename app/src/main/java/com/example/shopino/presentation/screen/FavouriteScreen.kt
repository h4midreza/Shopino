package com.example.shopino.presentation.screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.shopino.R
import com.example.shopino.domain.model.ItemsModel
import com.example.shopino.navigation.ScreenRoutes
import com.example.shopino.presentation.component.common.StandardTopAppBar
import com.example.shopino.presentation.viewmodel.FavouriteViewModel
import com.google.gson.Gson

@Composable
fun FavouriteScreen(
    navController: NavController
) {
    val navGraphStoreOwner = remember(navController.currentBackStackEntry) {
        navController.getBackStackEntry("app_nav_graph")
    }
    val viewModel: FavouriteViewModel = hiltViewModel(navGraphStoreOwner)
    val favouriteItems by viewModel.favouriteItems.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize() 
    ) {
        StandardTopAppBar(
            title = stringResource(id = R.string.screen_title_favourites),
            onBackClick = { navController.popBackStack() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) 
        ) {
            if (favouriteItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(id = R.string.favourites_empty_message))
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(favouriteItems, key = { it.id }) { item ->
                        FavouriteListItem(
                            item = item,
                            onRemoveClick = {
                                viewModel.removeFavourite(item)
                            },
                            onItemClick = {
                                val gson = Gson()
                                val itemJson = gson.toJson(item)
                                navController.navigate(
                                    ScreenRoutes.Detail.route + "/${Uri.encode(itemJson)}"
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun FavouriteListItem(
    item: ItemsModel,
    onRemoveClick: () -> Unit,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = item.imageUrl.ifEmpty { R.drawable.placeholder },
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder)
                ),
                contentDescription = item.title,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.item_recommended_price_format, item.price),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold, 
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f) 
                    )
                    Image(
                        painter = painterResource(id = R.drawable.star), 
                        contentDescription = stringResource(id = R.string.detail_rating_format), 
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.detail_rating_format, item.ratingRate),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant 
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                       text = stringResource(R.string.rating_count_format, item.ratingCount),
                       fontSize = 12.sp,
                       color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            IconButton(onClick = onRemoveClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.favourites_remove_button_desc), 
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

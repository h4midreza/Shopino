package com.example.shopino.presentation.component.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp // Import dp
import androidx.compose.ui.unit.sp
import com.example.shopino.R

@Composable
fun Header(username: String, onSearchClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.header_padding_top),
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = stringResource(id = R.string.header_welcome_back))
            Text(
                text = username,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = stringResource(id = R.string.header_search_icon_description),
                modifier = Modifier.clickable {
                    // TODO: Navigate to Search Screen
                    onSearchClick()
                }
            )
        }
    }
}
package com.example.shopino.presentation.component.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Replay
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.shopino.R

@Composable
fun OrdersSection(
    current: Int,
    delivered: Int,
    returned: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.orders_section_padding_horizontal)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OrderItem(
            title = stringResource(id = R.string.profile_order_item_current),
            count = current,
            icon = Icons.Default.CloudQueue
        )
        OrderItem(
            title = stringResource(id = R.string.profile_order_item_delivered),
            count = delivered,
            icon = Icons.Default.CheckCircle
        )
        OrderItem(
            title = stringResource(id = R.string.profile_order_item_returned),
            count = returned,
            icon = Icons.Default.Replay
        )
    }
}

package com.example.shopino.presentation.component.profile

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.shopino.R

@Composable
fun QuickAccessSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.quick_access_section_padding_horizontal))
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.quick_access_horizontal_arrangement_space))
    ) {
        ActionCard(
            titleResId = R.string.profile_quick_access_increase_balance,
            icon = Icons.Default.AccountBalanceWallet,
            backgroundColor = colorResource(id = R.color.quick_access_balance_bg),
            onClick = { /* TODO: Handle Increase Balance click */ }
        )
        ActionCard(
            titleResId = R.string.profile_quick_access_buy_sell,
            icon = Icons.Default.AttachMoney,
            backgroundColor = colorResource(id = R.color.quick_access_trade_bg),
            onClick = { /* TODO: Handle Buy & Sell click */ }
        )
        ActionCard(
            titleResId = R.string.profile_quick_access_shopino_missions,
            icon = Icons.Default.EmojiEvents,
            backgroundColor = colorResource(id = R.color.quick_access_missions_bg),
            onClick = { /* TODO: Handle Shopino Missions click */ }
        )
    }
}

@Composable
fun ActionCard(
    titleResId: Int,
    icon: ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier
            .width(dimensionResource(id = R.dimen.action_card_width))
            .height(dimensionResource(id = R.dimen.action_card_height)),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.action_card_row_padding)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = R.string.profile_action_card_icon_content_description),
                tint = colorResource(id = R.color.white)
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.action_card_icon_spacer_width)))
            Text(
                text = stringResource(id = titleResId),
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

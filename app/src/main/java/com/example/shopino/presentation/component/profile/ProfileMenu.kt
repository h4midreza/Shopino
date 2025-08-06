package com.example.shopino.presentation.component.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.shopino.R

data class ProfileMenuItem(val titleResId: Int, val icon: ImageVector, val onClickAction: () -> Unit = {})

@Composable
fun ProfileMenu() {
    val items = listOf(
        ProfileMenuItem(R.string.profile_menu_item_orders, Icons.Default.ShoppingBag, onClickAction = { /*TODO: Navigate to Orders*/ }),
        ProfileMenuItem(R.string.profile_menu_item_my_lists, Icons.Default.FavoriteBorder, onClickAction = { /*TODO: Navigate to My Lists*/ }),
        ProfileMenuItem(R.string.profile_menu_item_addresses, Icons.Default.LocationOn, onClickAction = { /*TODO: Navigate to Addresses*/ }),
        ProfileMenuItem(R.string.profile_menu_item_messages, Icons.Default.MailOutline, onClickAction = { /*TODO: Navigate to Messages*/ }),
        ProfileMenuItem(R.string.profile_menu_item_recent_views, Icons.Default.History, onClickAction = { /*TODO: Navigate to Recent Views*/ }),
        ProfileMenuItem(R.string.profile_menu_item_account_info, Icons.Default.PersonOutline, onClickAction = { /*TODO: Navigate to Account Info*/ }),
        ProfileMenuItem(R.string.profile_menu_item_settings, Icons.Default.Settings, onClickAction = { /*TODO: Navigate to Settings*/ }),
        ProfileMenuItem(R.string.profile_menu_item_logout, Icons.Default.ExitToApp, onClickAction = { /*TODO: Handle Logout*/ })
    )

    Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.profile_menu_padding_horizontal))) {
        items.forEach { item ->
            ListItem(
                headlineContent = { Text(stringResource(id = item.titleResId)) },
                leadingContent = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(id = R.string.profile_menu_icon_content_description)
                    )
                },
                modifier = Modifier
                    .clickable { item.onClickAction() }
                    .padding(vertical = dimensionResource(id = R.dimen.profile_menu_item_padding_vertical))
            )
            Divider()
        }
    }
}

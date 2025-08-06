package com.example.shopino.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopino.R
import com.example.shopino.presentation.component.profile.IdentityVerificationCard
import com.example.shopino.presentation.component.profile.OrdersSection
import com.example.shopino.presentation.component.profile.ProfileHeader
import com.example.shopino.presentation.component.profile.ProfileMenu
import com.example.shopino.presentation.component.profile.QuickAccessSection
import com.example.shopino.presentation.uistate.ProfileUiState
import com.example.shopino.presentation.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding() 
            .verticalScroll(rememberScrollState())
    ) {
        ProfileHeader(
            name = state.username,
            phone = state.phoneNumber
        )

        Spacer(Modifier.height(dimensionResource(id = R.dimen.profile_spacer_height)))

        QuickAccessSection()

        Spacer(Modifier.height(dimensionResource(id = R.dimen.profile_section_spacer_height)))

        IdentityVerificationCard()

        Spacer(Modifier.height(dimensionResource(id = R.dimen.profile_section_spacer_height)))

        OrdersSection(
            current = state.currentOrders,
            delivered = state.deliveredOrders,
            returned = state.returnedOrders
        )

        Divider(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.profile_divider_padding_vertical)))

        ProfileMenu()
    }
}

@Preview(showBackground = true, locale = "fa")
@Composable
fun ProfileScreenPreview() {
    val fakeViewModel = object : ProfileViewModel() {
        override val uiState = MutableStateFlow(
            ProfileUiState()
        ).asStateFlow()
    }
    ProfileScreen(viewModel = fakeViewModel)
}

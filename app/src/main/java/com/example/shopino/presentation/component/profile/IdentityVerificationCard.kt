package com.example.shopino.presentation.component.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.shopino.R

@Composable
fun IdentityVerificationCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.identity_card_padding_horizontal)),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.identity_card_background))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.identity_card_column_padding))) {
            Text(
                text = stringResource(id = R.string.identity_verification_message),
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.identity_card_message_text)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.identity_card_spacer_height)))
            TextButton(onClick = { /* TODO */ }) {
                Text(stringResource(id = R.string.identity_verification_button), color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

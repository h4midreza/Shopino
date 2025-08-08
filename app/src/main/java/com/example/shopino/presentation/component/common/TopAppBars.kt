package com.example.shopino.presentation.component.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.shopino.R

@Composable
fun StandardTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    actions: @Composable (() -> Unit)? = null
) {
    val horizontalPadding = dimensionResource(id = R.dimen.top_app_bar_padding_horizontal)
    val verticalPadding = dimensionResource(id = R.dimen.top_app_bar_padding_vertical)

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = horizontalPadding,
                end = horizontalPadding,
                top = verticalPadding,
                bottom = verticalPadding
            )
    ) {
        val (backButtonRef, titleRef, actionsRef) = createRefs()

        if (showBackButton) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back_button_description),
                modifier = Modifier
                    .clickable { onBackClick() }
                    .constrainAs(backButtonRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        centerVerticallyTo(titleRef)
                    }
            )
        }

        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.font_size_title).value.sp,
            modifier = Modifier
                .constrainAs(titleRef) {
                    centerTo(parent)
                }
                .fillMaxWidth()
        )

        if (actions != null) {
            Box(modifier = Modifier.constrainAs(actionsRef) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                centerVerticallyTo(titleRef)
            }) {
                actions()
            }
        }
    }
}

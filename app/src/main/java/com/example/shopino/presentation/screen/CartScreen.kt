package com.example.shopino.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp // Keep for ViewModel interactions or complex logic not suitable for dimens.xml
import androidx.compose.ui.unit.sp // Keep for direct sp usage if needed, or convert from dimensionResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.shopino.R
import com.example.shopino.domain.model.ItemsModel
import com.example.shopino.presentation.viewmodel.CartViewModel
import kotlin.math.round

@Composable
fun CartScreen(
    onBackClick: () -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val itemsFromVm by viewModel.cartItems.collectAsState()
    val totalFee by viewModel.totalPrice.collectAsState()

    val percentTax = 0.02 // This could also be a constant or from a configuration
    val taxValue = round(totalFee * percentTax * 100) / 100.0

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = dimensionResource(id = R.dimen.padding_bottom_for_summary))
        ) {
            TopBar(onBackClick)

            if (itemsFromVm.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.cart_empty_message),
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp // Example of using sp conversion
                )
            } else {
                CartList(
                    cartItems = itemsFromVm,
                    onPlusClick = { item -> viewModel.increaseItemQuantity(item) },
                    onMinusClick = { item -> viewModel.decreaseItemQuantity(item) },
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            }
        }

        if (itemsFromVm.isNotEmpty()) {
            Surface(
                tonalElevation = dimensionResource(id = R.dimen.elevation_medium),
                shadowElevation = dimensionResource(id = R.dimen.elevation_medium),
                color = Color.White, // Consider R.color.white
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.White) // Consider R.color.white
                    .padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                CartSummary(
                    itemTotal = totalFee,
                    tax = taxValue,
                    delivery = 10.0 // Assuming fixed delivery, could be a constant or from config
                )
            }
        }
    }
}

@Composable
fun CartSummary(itemTotal: Double, tax: Double, delivery: Double) {
    val total = itemTotal + tax + delivery
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.cart_summary_padding_top))
    ) {
        @Composable
        fun summaryRow(label: String, value: Double) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.cart_summary_row_vertical_padding))
            ) {
                Text(
                    text = label,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    fontSize = dimensionResource(id = R.dimen.font_size_normal).value.sp
                )
                Text(
                    text = stringResource(R.string.cart_item_price_label, value), // Example for formatted string
                    // text = "%.2f".format(value), // Or keep this if simple formatting is enough
                    fontSize = dimensionResource(id = R.dimen.font_size_normal).value.sp
                )
            }
        }

        summaryRow(stringResource(id = R.string.cart_item_total_label), itemTotal)
        summaryRow(stringResource(id = R.string.cart_tax_label), tax)
        summaryRow(stringResource(id = R.string.cart_delivery_label), delivery)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.cart_divider_height))
                .background(colorResource(id = R.color.grey)) // Ensure R.color.grey exists
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.padding_large))
        ) {
            Text(
                text = stringResource(id = R.string.cart_total_label),
                Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = Color.Gray, // Consider R.color.text_secondary
                fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp
            )
            Text(
                text = stringResource(R.string.cart_item_price_label, total),
                fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
                fontWeight = FontWeight.Bold // Make total bold
            )
        }

        Button(
            onClick = { /* TODO: Implement Checkout with ViewModel */ },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_medium)),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.purple) // Ensure R.color.purple exists
            ),
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.padding_large))
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.cart_checkout_button_height))
        ) {
            Text(
                text = stringResource(id = R.string.cart_checkout_button),
                fontSize = dimensionResource(id = R.dimen.font_size_large).value.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun CartList(
    cartItems: List<ItemsModel>,
    onPlusClick: (ItemsModel) -> Unit,
    onMinusClick: (ItemsModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(top = dimensionResource(id = R.dimen.cart_lazy_column_padding_top))
    ) {
        items(cartItems) { item ->
            CardItem(
                item = item,
                onPlusClick = { onPlusClick(item) },
                onMinusClick = { onMinusClick(item) }
            )
        }
    }
}


@Composable
private fun CardItem(
    item: ItemsModel,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.cart_item_vertical_padding))
    ) {
        val (pic, titleTxt, feeEachTime, totalEachItem, quantity) = createRefs()

        Image(
            painter = rememberAsyncImagePainter(item.picUrl.firstOrNull()),
            contentDescription = stringResource(id = R.string.cart_item_image_description, item.title),
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.cart_item_image_size))
                .background(
                    color = colorResource(id = R.color.lightGrey), // Ensure R.color.lightGrey exists
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_medium))
                )
                .padding(dimensionResource(id = R.dimen.cart_item_image_padding))
                .constrainAs(pic) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = item.title,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
            modifier = Modifier.constrainAs(titleTxt) {
                start.linkTo(pic.end, margin = 12.dp)
                top.linkTo(parent.top)
            }
        )

        Text(
            text = stringResource(id = R.string.cart_item_fee_each_time_format, item.price),
            // text = "${item.price}", // Original
            color = colorResource(id = R.color.purple),
            fontSize = dimensionResource(id = R.dimen.font_size_normal).value.sp,
            modifier = Modifier.constrainAs(feeEachTime) {
                start.linkTo(pic.end, margin = 12.dp)
                top.linkTo(titleTxt.bottom, margin = 4.dp)
            }
        )

        Text(
            text = stringResource(id = R.string.cart_item_total_each_label, item.numberInCart * item.price),
            fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.constrainAs(totalEachItem) {
                start.linkTo(pic.end, margin = 12.dp)
                top.linkTo(feeEachTime.bottom, margin = 4.dp)
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .constrainAs(quantity) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .background(
                    color = colorResource(id = R.color.lightGrey),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_medium))
                )
                .padding(
                    horizontal = dimensionResource(id = R.dimen.cart_item_quantity_row_horizontal_padding),
                    vertical = dimensionResource(id = R.dimen.cart_item_quantity_row_vertical_padding)
                )
        ) {
            IconButton(onClick = onMinusClick) {
                Text(
                    stringResource(id = R.string.cart_decrease_quantity_button),
                    fontSize = dimensionResource(id = R.dimen.font_size_xlarge).value.sp,
                    color = Color.Black // Consider R.color.text_primary
                )
            }

            Text(
                text = item.numberInCart.toString(),
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.cart_item_quantity_text_padding)),
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.font_size_medium).value.sp
            )

            IconButton(onClick = onPlusClick) {
                Text(
                    stringResource(id = R.string.cart_increase_quantity_button),
                    fontSize = dimensionResource(id = R.dimen.font_size_xlarge).value.sp,
                    color = colorResource(id = R.color.purple)
                )
            }
        }
    }
}

@Composable
private fun TopBar(onBackClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .padding(
                top = dimensionResource(id = R.dimen.cart_top_bar_padding_top),
                bottom = dimensionResource(id = R.dimen.cart_top_bar_padding_bottom),
                start = dimensionResource(id = R.dimen.cart_top_bar_horizontal_padding),
                end = dimensionResource(id = R.dimen.cart_top_bar_horizontal_padding)
            )
            .fillMaxWidth() // Added for the text to truly center
    ) {
        val (backBtn, cartTxt) = createRefs()
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(cartTxt) { centerTo(parent) },
            text = stringResource(id = R.string.cart_screen_title),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.font_size_title).value.sp
        )

        Image(
            painter = painterResource(id = R.drawable.back), // Ensure R.drawable.back exists
            contentDescription = stringResource(id = R.string.cart_back_button_description),
            modifier = Modifier
                .clickable { onBackClick() }
                .constrainAs(backBtn) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    centerVerticallyTo(cartTxt) // Center vertically with the title
                }
        )
    }
}

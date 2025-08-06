package com.example.shopino.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.shopino.R
import com.example.shopino.domain.model.ItemsModel
import com.example.shopino.model.testRecommended
import com.example.shopino.presentation.component.ListItemsFullSize
import com.example.shopino.presentation.viewmodel.MainViewModel

@Composable
fun ListItemScreen(
    title: String,
    onBackClick: () -> Unit,
    viewModel: MainViewModel,
    id: String,
    navController: NavController
) {
    val item by viewModel.recommended.observeAsState(emptyList())
    var isLoading by remember { mutableStateOf(true) }
    val Filtered = remember { mutableStateListOf<ItemsModel>() }
    var showFilteredLoading by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        val testFiltered = testRecommended()
        Filtered.clear()
        Filtered.addAll(testFiltered)
        showFilteredLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ConstraintLayout(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
            val (backBtn, cartTxt) = createRefs()

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(cartTxt) { centerTo(parent) },
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                text = title
            )

            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onBackClick()
                    }
                    .constrainAs(backBtn) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            ListItemsFullSize(items = Filtered, navController = navController)
        }
    }

    LaunchedEffect(item) {
        isLoading = Filtered.isEmpty()
    }
}
package com.example.shelfy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.composables.BottomBar
import com.example.shelfy.ui.composables.ContentHomePage
import com.example.shelfy.ui.composables.TopBar
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlackPage

@Composable
fun HomePage(
    viewModel : AppViewModel,
    navController : NavHostController
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = BlackPage)
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        ContentHomePage(
            viewModel = viewModel,
            navController = navController,
            modifier = Modifier
                .weight(10f)
                .verticalScroll(rememberScrollState())
        )
        BottomBar(
            viewModel,
            navController,
            modifier = Modifier
                .background(color = BlackBar)
                .fillMaxWidth()
        )
    }
}
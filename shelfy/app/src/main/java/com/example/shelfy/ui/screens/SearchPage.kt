package com.example.shelfy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shelfy.ui.BookHomePageViewModel
import com.example.shelfy.ui.composables.BottomBar
import com.example.shelfy.ui.composables.ContentSearchPage
import com.example.shelfy.ui.composables.TopBar
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlackPage

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Search(
    viewModel : BookHomePageViewModel,
    navController : NavHostController
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = BlackPage)
    ) {
        TopBar(
            modifier = Modifier
                .weight(1f)
                .background(color = BlackBar)
        )
        ContentSearchPage(
            viewModel = viewModel,
            navController = navController,
            modifier = Modifier
                .background(color = BlackPage)
                .weight(10f)
                .fillMaxWidth())
        BottomBar(
            navController,
            modifier = Modifier
                .background(color = BlackBar)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun preview(){
    Search(viewModel = BookHomePageViewModel(), navController = rememberNavController())
}

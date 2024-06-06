package com.example.shelfy.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.shelfy.MainActivity
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.composables.BarUser
import com.example.shelfy.ui.composables.BottomBar
import com.example.shelfy.ui.composables.ContentProfilePage
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlackPage

@Composable
fun ProfilePage(
    viewModel: AppViewModel,
    navController: NavHostController,
    mainActivity: MainActivity
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(brush = Brush.verticalGradient(
                colors = listOf(BlackBar, BlackPage)
            )),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        BarUser(
            viewModel = viewModel,
            navController = navController,
            mainActivity = mainActivity
        )
        ContentProfilePage(
            viewModel = viewModel,
            navController = navController,
            mainActivity = mainActivity,
            modifier = Modifier
                .weight(10f)
                .padding(8.dp)
        )
        BottomBar(
            viewModel,
            navController,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
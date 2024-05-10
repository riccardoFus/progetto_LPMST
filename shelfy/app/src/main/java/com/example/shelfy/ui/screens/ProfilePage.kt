package com.example.shelfy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shelfy.R
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.composables.BarUser
import com.example.shelfy.ui.composables.BottomBar
import com.example.shelfy.ui.composables.ContentProfilePage
import com.example.shelfy.ui.composables.TopBar
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlackPage
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.fonts

@Composable
fun ProfilePage(
    viewModel : AppViewModel,
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
        BarUser(viewModel = viewModel, navController = navController)

        BarUser(
            viewModel = viewModel,
            navController = navController
        )
        ContentProfilePage(
            viewModel = viewModel,
            navController = navController,
            modifier = Modifier
                .weight(10f)
                .padding(8.dp)
        )
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
fun Preview(){
    ProfilePage(viewModel = AppViewModel(), navController = rememberNavController())
}
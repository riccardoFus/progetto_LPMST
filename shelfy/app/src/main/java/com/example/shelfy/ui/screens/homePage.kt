package com.example.shelfy.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shelfy.ui.BookHomePageViewModel
import com.example.shelfy.R
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlackPage
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.fonts

@Composable
fun HomePage(
    viewModel : BookHomePageViewModel,
    navController : NavHostController
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = BlackPage)
    ) {
        Box(
            modifier = Modifier
                .weight(0.9f)
                .background(color = BlackBar),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "SHELFY",
                color = BlueText,
                fontFamily = fonts,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
        Box(modifier = Modifier
            .weight(10f)
            .verticalScroll(rememberScrollState())){
            Column {
                Text(text = "Raccomandazione 1", color = BlueText, modifier = Modifier.padding(8.dp))
                Row (modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://"+viewModel.booksUiState.data?.items?.get(0)?.volumeInfo?.imageLinks?.thumbnail?.substring(7))
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .width(200.dp)
                            .height(300.dp)
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://"+viewModel.booksUiState.data?.items?.get(1)?.volumeInfo?.imageLinks?.thumbnail?.substring(7))
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .width(200.dp)
                            .height(300.dp)
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://"+viewModel.booksUiState.data?.items?.get(0)?.volumeInfo?.imageLinks?.thumbnail?.substring(7))
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .width(200.dp)
                            .height(300.dp)
                    )
                }

            }

        }

            Row (
                modifier = Modifier
                    .background(color = BlackBar)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.search_outline_1024x1024),
                        contentDescription = "Search",
                        tint = BlueText,
                        modifier = Modifier
                            .weight(1f)
                            .size(30.dp)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.home_1024x919),
                        contentDescription = "Search",
                        tint = BlueText,
                        modifier = Modifier
                            .weight(1f)
                            .size(30.dp)
                    )
                }
                IconButton(onClick = {navController.navigate("PROFILE_SCREEN")}) {
                    Icon(
                        painter = painterResource(id = R.drawable.profile_icon_512x512_w0uaq4yr),
                        contentDescription = "Search",
                        tint = BlueText,
                        modifier = Modifier
                            .weight(1f)
                            .size(30.dp)
                    )
            }
        }
    }
}
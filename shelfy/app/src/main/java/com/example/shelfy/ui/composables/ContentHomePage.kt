package com.example.shelfy.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.fonts

@Composable
fun ContentHomePage(
    viewModel : AppViewModel,
    navController : NavHostController,
    modifier : Modifier = Modifier,
){
    Box(
        modifier = modifier
    ){
        Column () {
            Text(
                text = "Thriller",
                color = BlueText,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp),
                fontSize = 20.sp,
                fontFamily = fonts,
                textAlign = TextAlign.Justify)
            LazyRow{
                items(viewModel.booksUiStateRecommendation1.data?.items ?: emptyList()){ item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(300.dp)
                            .width(200.dp)
                            .padding(8.dp)
                    ) {
                        BookCardHomePage(
                            item = item,
                            viewModel = viewModel,
                            navController = navController,
                            page = "homepage",
                            readlist = "None"
                        )
                    }
                }
            }
            Text(
                text = "Avventura",
                color = BlueText,
                modifier = Modifier
                    .padding(start = 8.dp),
                fontSize = 20.sp,
                fontFamily = fonts,
                textAlign = TextAlign.Justify)
            LazyRow{
                items(viewModel.booksUiStateRecommendation2.data?.items ?: emptyList()){ item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(300.dp)
                            .width(200.dp)
                            .padding(8.dp)
                    ) {
                        BookCardHomePage(
                            item = item,
                            viewModel = viewModel,
                            navController = navController,
                            page = "homepage",
                            readlist = "None"
                        )
                    }
                }
            }
            Text(
                text = "Fantasy",
                color = BlueText,
                modifier = Modifier
                    .padding(start = 8.dp),
                fontSize = 20.sp,
                fontFamily = fonts,
                textAlign = TextAlign.Justify)
            LazyRow{
                items(viewModel.booksUiStateRecommendation3.data?.items ?: emptyList()){ item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(300.dp)
                            .width(200.dp)
                            .padding(8.dp)
                    ) {
                        BookCardHomePage(
                            item = item,
                            viewModel = viewModel,
                            navController = navController,
                            page = "homepage",
                            readlist = "None"
                        )
                    }
                }
            }
        }
    }
}
package com.example.shelfy.ui.screens

import android.content.SearchRecentSuggestionsProvider
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shelfy.R
import com.example.shelfy.ui.BookHomePageViewModel
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlackPage
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.WhiteText
import com.example.shelfy.ui.theme.fonts

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
        Box(
            modifier = Modifier
                .background(color = BlackBar)
                .weight(0.9f)
                .fillMaxWidth(),
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

        Box(
            modifier = Modifier
                .background(color = BlackPage)
                .weight(10f)
                .fillMaxWidth(),
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ){
                var text by rememberSaveable { mutableStateOf("") }
                var query by rememberSaveable {
                    mutableStateOf("")
                }
                val keyboardController = LocalSoftwareKeyboardController.current
                TextField(
                    singleLine = true,
                    value = text,
                    placeholder = { Text(text = "Che libro vuoi leggere?") },
                    onValueChange = { newText -> text = newText },
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .widthIn(400.dp)
                        .onKeyEvent {
                            if (it.key == Key.Enter) {
                                query = text.replace(" ", "+")
                                viewModel.getBooksByQuery(query)
                                keyboardController?.hide()
                            }
                            true
                        },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        query = text.replace(" ", "+")
                        viewModel.getBooksByQuery(query)
                        keyboardController?.hide()
                    })
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ){
                    items(viewModel.booksSearchUiState.data?.items ?: emptyList()){ item ->
                        Row(modifier = Modifier
                            .padding(8.dp)
                            .height(250.dp)) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        ("https://" + (item?.volumeInfo?.imageLinks?.thumbnail?.substring(
                                            7
                                        ) ?: "store.bookbaby.com/BookShop/CommonControls/BookshopThemes/bookshop/OnePageBookCoverImage.jpg?BookID=BK00014296&abOnly=False&ImageType=Back"))
                                    )
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(180.dp)
                                    .height(250.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            8.dp
                                        )
                                    )
                            )
                            Column(modifier = Modifier.fillMaxWidth().padding(start = 8.dp)) {
                                Text(text = (item?.volumeInfo?.title ?: "No Titolo"), color = WhiteText, fontSize = 15.sp,
                                    modifier = Modifier
                                        .padding(top = 2.dp), fontWeight = FontWeight.SemiBold, fontFamily = fonts)
                                Text(
                                    text = (item?.volumeInfo?.authors ?: "No Autori").toString().replace("[", "").replace("]", ""), color = WhiteText, fontSize = 13.sp,
                                    modifier = Modifier
                                        .padding(top = 2.dp),
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = fonts)
                                Text(text = "Trama : " + (item?.volumeInfo?.description ?: "Non presente"), color = WhiteText, fontSize = 11.sp,
                                    modifier = Modifier
                                        .padding(top = 2.dp), fontWeight = FontWeight.SemiBold, fontFamily = fonts,
                                    overflow = TextOverflow.Ellipsis)
                            }
                        }
                    }
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
            IconButton(onClick = {navController.navigate("HOME_SCREEN")}) {
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

@Preview
@Composable
fun preview(){
    Search(viewModel = BookHomePageViewModel(), navController = rememberNavController())
}

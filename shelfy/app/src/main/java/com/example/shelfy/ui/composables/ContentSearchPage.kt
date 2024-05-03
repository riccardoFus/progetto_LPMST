package com.example.shelfy.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shelfy.ui.BookHomePageViewModel
import com.example.shelfy.ui.theme.BlackPage
import com.example.shelfy.ui.theme.fonts

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContentSearchPage(
    viewModel : BookHomePageViewModel,
    navController : NavHostController,
    modifier : Modifier = Modifier
){
    Box(
        modifier = modifier
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
                placeholder = {
                    Text(
                        text = "Cerca libro...",
                        fontFamily = fonts,
                        fontSize = 20.sp
                    )
                },
                onValueChange = {
                        newText -> text = newText
                },
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
                textStyle = TextStyle(
                    fontFamily = fonts,
                    fontSize = 20.sp
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
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
                        BookCardSearchPage(item = item, viewModel = viewModel, navController = navController)
                    }
                }
            }
        }
    }
}
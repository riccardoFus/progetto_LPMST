package com.example.shelfy.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shelfy.R
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.isValidPassword
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.fonts

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContentSearchPage(
    viewModel : AppViewModel,
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
            OutlinedTextField(
                singleLine = true,
                value = text,
                placeholder = {
                    Text(
                        text = stringResource(R.string.cerca_libro),
                        fontFamily = fonts,
                        fontSize = 20.sp
                    )
                },
                onValueChange = {
                        newText -> text = newText
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(BlackBar)
                    .padding(8.dp)
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
                    }),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = BlueText,
                    unfocusedBorderColor = BlueText,
                    unfocusedLabelColor = BlueText,
                    unfocusedLeadingIconColor = BlueText,
                    focusedTextColor = BlueText,
                    focusedBorderColor = BlueText,
                    focusedLabelColor = BlueText,
                    focusedLeadingIconColor = BlueText,
                    cursorColor = BlueText,
                    unfocusedPlaceholderColor = BlueText,
                    focusedPlaceholderColor = BlueText
                )
            )
            // output of the search
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ){
                items(viewModel.booksSearchUiState.data?.items ?: emptyList()){ item ->
                    Row(modifier = Modifier
                        .padding(8.dp)
                        .height(250.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(BlackBar)) {
                        BookCardSearchPage(item = item, viewModel = viewModel, navController = navController)
                    }
                }
            }
        }
    }
}
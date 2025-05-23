package com.example.shelfy.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.shelfy.R
import com.example.shelfy.data.remote.responses.Books
import com.example.shelfy.data.remote.responses.Item
import com.example.shelfy.navigation.Screens
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.WhiteText
import com.example.shelfy.util.Resource

@Composable
fun BottomBar(
    viewModel : AppViewModel,
    navController : NavHostController,
    modifier : Modifier = Modifier
){
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        // IconButton to navigate to Search Page
        IconButton(onClick = {
            viewModel.booksSearchUiState = Resource.Loading<Books>()
            viewModel.page = "Search"
            navController.navigate(Screens.SEARCH_SCREEN)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.search_outline_1024x1024),
                contentDescription = stringResource(R.string.search),
                tint = BlueText,
                modifier = Modifier
                    .weight(1f)
                    .size(if(viewModel.page == "Search") 34.dp else 25.dp)
            )
        }
        // IconButton to navigate to Home Page
        IconButton(onClick = {
            viewModel.page = "Homepage"
            navController.navigate(Screens.HOME_SCREEN)

        }) {
            Icon(
                painter = painterResource(id = R.drawable.home_1024x919),
                contentDescription = stringResource(R.string.home),
                tint = BlueText,
                modifier = Modifier
                    .weight(1f)
                    .size(if(viewModel.page == "Homepage") 34.dp else 25.dp)
            )
        }

        // IconButton to navigate to Profile Page
        IconButton(onClick = {
            viewModel.page = "Profile"
            navController.navigate(Screens.PROFILE_SCREEN)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.profile_icon_512x512_w0uaq4yr),
                contentDescription = stringResource(R.string.profile),
                tint = BlueText,
                modifier = Modifier
                    .weight(1f)
                    .size(if(viewModel.page == "Profile") 34.dp else 25.dp)
            )
        }
    }
}
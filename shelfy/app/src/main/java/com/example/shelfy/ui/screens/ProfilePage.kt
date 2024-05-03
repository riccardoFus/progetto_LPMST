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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shelfy.R
import com.example.shelfy.ui.BookHomePageViewModel
import com.example.shelfy.ui.composables.BottomBar
import com.example.shelfy.ui.composables.TopBar
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlackPage
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.fonts

@Composable
fun Profilo(
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
        Box(modifier = Modifier
            .weight(10f)
            .padding(8.dp)){

            Row (modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Icon(
                    painter = painterResource(id = R.drawable.profile_icon_512x512_w0uaq4yr),
                    contentDescription = "Profilo",
                    tint = BlueText,
                    modifier = Modifier
                        .size(47.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "La tua libreria",
                    fontFamily = fonts,
                    fontSize = 24.sp,
                    color = BlueText
                )

                Spacer(modifier = Modifier.width(15.dp))
                var addBook by remember {mutableStateOf(false)}
                IconButton(
                    onClick = {addBook = true}
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.add_circle_plus_1024x1024),
                        contentDescription = "Aggiungi libro",
                        tint = BlueText,
                        modifier = Modifier
                            .size(28.dp))

                }
                IconButton(
                    onClick = {navController.navigate("SEARCH_SCREEN")}
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.search_outline_1024x1024),
                        contentDescription = "Cerca libro",
                        tint = BlueText,
                        modifier = Modifier
                            .size(28.dp))
                }

                if (addBook) {
                    Dialog(onDismissRequest = { addBook = false }) {
                        LazyRow{
                            /* TO DO*/
                         }
                    }
                }

                IconButton(
                    onClick = {}
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.sort_ascending_1024x1024),
                        contentDescription = "Ordina",
                        tint = BlueText,
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
            }
        }
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
    Profilo(viewModel = BookHomePageViewModel(), navController = rememberNavController())
}
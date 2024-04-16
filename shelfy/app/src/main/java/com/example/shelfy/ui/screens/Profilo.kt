package com.example.shelfy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shelfy.R
import com.example.shelfy.ui.BookHomePageViewModel
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
        Box(
            modifier = Modifier
                .weight(0.9f)
                .background(color = BlackBar)
                .fillMaxWidth()
                .fillMaxHeight(),
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

                IconButton(
                    onClick = {System.out.println("Aggiungi")}
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.add_circle_plus_1024x1024),
                        contentDescription = "Aggiungi libro",
                        tint = BlueText,
                        modifier = Modifier
                            .size(28.dp))

                }
                IconButton(
                    onClick = {}

                ){
                    Icon(
                        painter = painterResource(id = R.drawable.search_outline_1024x1024),
                        contentDescription = "Cerca libro",
                        tint = BlueText,
                        modifier = Modifier
                            .size(28.dp))
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
            IconButton(onClick = {}) {
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
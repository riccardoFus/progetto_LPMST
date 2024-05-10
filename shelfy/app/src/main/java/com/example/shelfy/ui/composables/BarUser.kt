package com.example.shelfy.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.shelfy.R
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.fonts

@Composable
fun BarUser(
    viewModel : AppViewModel,
    navController : NavHostController,
    modifier : Modifier = Modifier
){
    Row (
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Spacer(modifier = Modifier.width(2.dp))
        Icon(
            painter = painterResource(id = R.drawable.profile_icon_512x512_w0uaq4yr),
            contentDescription = "Profilo",
            tint = BlueText,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = stringResource(R.string.la_tua_libreria),
            fontFamily = fonts,
            fontSize = 24.sp,
            color = BlueText,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.width(65.dp))
        var addBook by remember { mutableStateOf(false) }
        IconButton(
            onClick = {addBook = true}
        ){
            Icon(
                painter = painterResource(id = R.drawable.add_circle_plus_1024x1024),
                contentDescription = stringResource(R.string.aggiungi_libro),
                tint = BlueText,
                modifier = Modifier
                    .size(28.dp))

        }
        IconButton(
            onClick = {navController.navigate("SEARCH_SCREEN")}
        ){
            Icon(
                painter = painterResource(id = R.drawable.search_outline_1024x1024),
                contentDescription = stringResource(id = R.string.cerca_libro),
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
            onClick = {
                viewModel.itemList.sortBy {
                    it.volumeInfo.title
                }
            }
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
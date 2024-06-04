package com.example.shelfy.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.shelfy.MainActivity
import com.example.shelfy.R
import com.example.shelfy.data.db.Readlist
import com.example.shelfy.data.remote.responses.Item
import com.example.shelfy.navigation.Screens
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.fonts
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun ContentProfilePage(
    viewModel: AppViewModel,
    navController : NavHostController,
    mainActivity : MainActivity,
    modifier : Modifier = Modifier,
){
    if(!viewModel.libraryUpdated) {
        viewModel.itemList.clear()
        viewModel.libraryUpdated = true
        viewModel.getElementsLibrary(
            viewModel.userId
        )
    }

    if(!viewModel.readlistsUpdated){
        viewModel.readlistsUpdated = true
        viewModel.getReadlists()
    }
    Box(
        modifier = modifier){

        Column(modifier = Modifier.fillMaxWidth()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(viewModel.readlists) { readlist ->
                    if (readlist.name.length > 0) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = readlist.name,
                                fontFamily = fonts,
                                fontSize = 20.sp,
                                color = BlueText,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier
                                    .padding(8.dp, bottom = 0.dp)
                                    .weight(1f),
                                fontWeight = FontWeight.SemiBold
                            )
                            if(readlist.name != stringResource(id = R.string.libreria)){
                                IconButton(onClick = { viewModel.deleteReadlist(readlist.name)
                                },
                                    modifier = Modifier) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_close_24),
                                        contentDescription = stringResource(R.string.aggiungi_una_nota),
                                        tint = BlueText,
                                        modifier = Modifier
                                            .size(35.dp)
                                            .weight(1f)
                                    )
                                }
                            }
                        }
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(if (readlist.content.isNotEmpty()) 360.dp else 0.dp)
                        ) {
                            items(readlist.content) { item ->
                                BookCardHomePage(
                                    item = item,
                                    viewModel = viewModel,
                                    navController = navController,
                                    page = "profile",
                                    readlist = readlist.name,
                                    mainActivity = mainActivity
                                )
                            }

                        }
                        if(readlist.content.isEmpty()){
                            Text(text = "(Vuota)",
                                fontFamily = fonts,
                                fontSize = 15.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 20.dp, bottom = 11.dp))
                        }
                    }
                }
            }
        }
    }
}
package com.example.shelfy.ui.composables

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.shelfy.data.db.Readlist
import com.example.shelfy.data.remote.responses.Item
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.fonts
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun ContentProfilePage(
    viewModel: AppViewModel,
    navController : NavHostController,
    modifier : Modifier = Modifier
){
    var done by rememberSaveable {
        mutableStateOf(false)
    }
    if(!done){
        done = true
        viewModel.itemList.clear()
        viewModel.getElementsLibrary(
            viewModel.userId
        )
    }
    Box(
        modifier = modifier){
            LazyColumn(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()){
                items(viewModel.itemList){ item ->
                    Row(modifier = Modifier
                        .padding(8.dp)
                        .height(250.dp)) {
                        BookCardSearchPage(item = item, viewModel = viewModel, navController = navController)
                    }
                }
            }
    }
}


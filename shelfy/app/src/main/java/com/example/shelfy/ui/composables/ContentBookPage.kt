package com.example.shelfy.ui.composables

import android.annotation.SuppressLint
import android.content.Intent
import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shelfy.R
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlackPage
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.WhiteText
import com.example.shelfy.ui.theme.fonts
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.max

@SuppressLint("SuspiciousIndentation", "CoroutineCreationDuringComposition")
@Composable
fun ContentBookPage(
    viewModel : AppViewModel,
    modifier : Modifier = Modifier
){
    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            val id = viewModel.bookUiState.data?.id
            var noteVisualizer by rememberSaveable { mutableStateOf(false) }

            var reviewEnabled by rememberSaveable { mutableStateOf(false) }
            var review by rememberSaveable { mutableStateOf(0) }
            var showReadlists by remember {mutableStateOf(false)}

            if(!viewModel.readlistsUpdated) {
                viewModel.readlistsUpdated = true
                viewModel.getReadlists()
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        "https://" + if((viewModel.bookUiState.data?.volumeInfo?.imageLinks?.large?.length ?: 0) > 0){
                            viewModel.bookUiState.data?.volumeInfo?.imageLinks?.large?.substring(7)
                        }else if((viewModel.bookUiState.data?.volumeInfo?.imageLinks?.small?.length ?: 0) > 0){
                            viewModel.bookUiState.data?.volumeInfo?.imageLinks?.small?.substring(7)
                        }else if((viewModel.bookUiState.data?.volumeInfo?.imageLinks?.thumbnail?.length ?: 0) > 0){
                            viewModel.bookUiState.data?.volumeInfo?.imageLinks?.thumbnail?.substring(7)
                        }else if((viewModel.bookUiState.data?.volumeInfo?.imageLinks?.smallThumbnail?.length ?: 0) > 0){
                            viewModel.bookUiState.data?.volumeInfo?.imageLinks?.smallThumbnail?.substring(7)
                        }else{
                            stringResource(R.string.book_image_no_available)
                        }
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .height(250.dp)
                    .width(180.dp)
                    .clip(
                        RoundedCornerShape(
                            8.dp
                        )
                    ),
                contentScale = ContentScale.Crop,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = (viewModel.bookUiState.data?.volumeInfo?.title
                            ?: stringResource(id = R.string.no_titolo)),
                        color = BlueText,
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .fillMaxWidth(),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = fonts,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = (viewModel.bookUiState.data?.volumeInfo?.authors.toString()
                            .replace("[", "").replace("]", "")),
                        color = WhiteText,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .fillMaxWidth(),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = fonts,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Column (modifier = Modifier.padding(top = 11.dp, start = 11.dp)){
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            viewModel.bookUiState.data?.volumeInfo?.infoLink
                        )
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null);
                    val context = LocalContext.current;

                    var more by rememberSaveable { mutableStateOf(false) }
                    IconButton(onClick = { more = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_more_vert_24),
                            contentDescription = stringResource(R.string.share),
                            tint = BlueText,
                            modifier = Modifier
                                .weight(1f)
                                .size(40.dp)
                        )
                    }
                    var aggiunto by remember{mutableStateOf(false)}
                    if(more){
                        DropdownMenu(
                            expanded = true,
                            onDismissRequest = { more = false },
                            modifier = Modifier
                                .background(BlackBar)
                        ) {
                            DropdownMenuItem(leadingIcon = {
                                 Icon(
                                    painter = painterResource(id = R.drawable.share_1024x896),
                                    contentDescription = stringResource(R.string.share),
                                    tint = BlueText,
                                    modifier = Modifier
                                        .weight(1f)
                                        .size(25.dp)
                                )
                            },
                                text = {
                                    Text(text = "Condividi libro", color = BlueText)},
                                onClick = { context.startActivity(shareIntent) },
                                modifier = Modifier
                                    .height(25.dp)
                            )
                            Spacer(modifier = Modifier
                                .size(10.dp))
                            DropdownMenuItem(leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_sticky_note_2_24),
                                    contentDescription = stringResource(R.string.share),
                                    tint = BlueText,
                                    modifier = Modifier
                                        .weight(1f)
                                        .size(25.dp)
                                )
                            },
                                text = {
                                    Text(text = "Visualizza nota", color = BlueText)},
                                onClick = {
                                    noteVisualizer = true; viewModel.getNota(viewModel.userId, id)
                                          },
                                modifier = Modifier
                                    .height(25.dp)
                            )
                            Spacer(modifier = Modifier
                                .size(10.dp))
                            DropdownMenuItem(leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.add_circle_plus_1024x1024),
                                    contentDescription = stringResource(R.string.aggiungi_alla_libreria),
                                    tint = BlueText,
                                    modifier = Modifier
                                        .weight(1f)
                                        .size(25.dp)
                                )
                            },
                                text = {
                                    Text(text = "Aggiungi alla libreria", color = BlueText)},
                                trailingIcon = {
                                    if(aggiunto) {
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_done_24),
                                            contentDescription = "Done",
                                            tint = BlueText,
                                            modifier = Modifier
                                                .weight(1f)
                                                .size(25.dp)
                                        )
                                    }
                                },
                                onClick = {
                                          viewModel.addToReadlist(viewModel.bookUiState.data, viewModel.userId, "Libreria")
                                    GlobalScope.launch {
                                        aggiunto = true
                                        Thread.sleep(2000)
                                        aggiunto = false
                                    }
                                },
                                modifier = Modifier
                                    .height(25.dp)

                            )
                            Spacer(modifier = Modifier
                                .size(10.dp))
                            DropdownMenuItem(leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.add_circle_plus_1024x1024),
                                    contentDescription = stringResource(R.string.aggiungi_a_una_readlist),
                                    tint = BlueText,
                                    modifier = Modifier
                                        .weight(1f)
                                        .size(25.dp)
                                )
                            },
                                text = {
                                    Text(text = "Aggiungi a una readlist", color = BlueText)},
                                onClick = {
                                    showReadlists = true
                                },
                                modifier = Modifier
                                    .height(25.dp)
                            )
                            Spacer(modifier = Modifier
                                .size(10.dp))
                            DropdownMenuItem(leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.add_circle_plus_1024x1024),
                                    contentDescription = stringResource(R.string.aggiungi_una_recensione),
                                    tint = BlueText,
                                    modifier = Modifier
                                        .weight(1f)
                                        .size(25.dp)
                                )
                            },
                                text = {
                                    Text(text = "Aggiungi una recensione", color = BlueText)},
                                onClick = {
                                    more = false
                                    reviewEnabled = true
                                },
                                modifier = Modifier
                                    .height(25.dp)
                            )
                        }
                    }
                }
            }
            var note = viewModel.note
            var testo by remember{ mutableStateOf("") }
            testo = note
                if (noteVisualizer) {
                    var noteAlreadyExists = viewModel.noteAlreadyExists
                    viewModel.checkNoteAlreadyInserted(viewModel.userId, id!!);
                    Dialog(onDismissRequest = { noteVisualizer = false }) {
                        TextField(singleLine = false,
                            value = testo,
                            placeholder = {
                                Text(
                                    text = "Nessuna nota" ,
                                    fontFamily = fonts,
                                    fontSize = 20.sp
                                )
                            },
                            onValueChange = {newText -> testo = newText},
                            textStyle = TextStyle(fontFamily = fonts, fontSize = 20.sp),
                            modifier = Modifier
                                .height(150.dp),
                            shape = RoundedCornerShape(30.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            trailingIcon = {
                                Column(modifier = Modifier.fillMaxHeight()) {
                                    IconButton(onClick = {
                                        noteVisualizer = false
                                        if(noteAlreadyExists)
                                            viewModel.updateNota(viewModel.userId, testo, id!!)
                                        else
                                            viewModel.addNota(viewModel.userId, testo, id!!)
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_close_24),
                                            contentDescription = stringResource(R.string.aggiungi_una_nota),
                                            modifier = Modifier
                                                .size(50.dp)
                                        )
                                    }
                                }
                            })

                    }
                }

            var showTrama by rememberSaveable { mutableStateOf(false) }
            var text = viewModel.bookUiState.data?.volumeInfo?.description
                ?: stringResource(id = R.string.trama_non_presente)
            text = text.replace("<p>","")
            text = text.replace("</p>", "")
            text = text.replace("<br>", "")
            text = text.replace("</br>", "")
            text = text.replace("<b>", "")
            text = text.replace("</b>", "")
            text = text.replace("<i>", "")
            text = text.replace("</i>", "")
            Text(
                text = text, color = WhiteText, fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .clickable { showTrama = !showTrama },
                fontWeight = FontWeight.SemiBold, fontFamily = fonts,
                textAlign = TextAlign.Left, overflow = TextOverflow.Ellipsis,
                maxLines = if (showTrama) Int.MAX_VALUE else 7
            )

            if(showReadlists) {
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = {showReadlists = false},
                    modifier = Modifier
                        .background(BlackBar)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .height(300.dp)
                            .width(165.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                    ) {
                        items(viewModel.readlists) { item ->
                            Column(modifier = Modifier
                                .padding(11.dp)
                                .align(Alignment.CenterHorizontally)){
                                OutlinedButton(onClick = { viewModel.addToReadlist(viewModel.bookUiState.data, viewModel.userId, item.name); showReadlists = false }, content = {Text(text = item.name, color = BlueText, fontFamily = fonts, fontSize = 20.sp)}, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(0.dp) )
                            }
                        }
                    }
                }
            }

            var reviews = viewModel.numberAndMediaReviews
            if(id != null) {
                viewModel.getReviews(id)
            }
            Text(
                text = reviews.first.toString() + " - " + reviews.second.toString(), color = WhiteText, fontSize = 16.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(), fontWeight = FontWeight.SemiBold, fontFamily = fonts,
                textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(color = BlackPage),
                contentAlignment = Alignment.TopStart
            ) {
                    if (reviewEnabled) {
                        Dialog(onDismissRequest = { reviewEnabled = false }) {
                            Column(modifier = Modifier
                                .background(Color.Transparent)
                                .width(300.dp)
                                .height(250.dp)
                                    ){
                                Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                                    IconButton(onClick = {review = 1}){
                                        Icon(
                                            painter = painterResource(id = if (review > 0) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24) ,
                                            contentDescription = stringResource(R.string._1_stella),
                                            tint = Color.Yellow,
                                            modifier = Modifier
                                                .size(70.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(11.dp))
                                    IconButton(onClick = {review = 2}){
                                        Icon(
                                            painter = painterResource(id = if (review > 1) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24),
                                            contentDescription = stringResource(R.string._2_stelle),
                                            tint = Color.Yellow,
                                            modifier = Modifier
                                                .size(70.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(11.dp))
                                    IconButton(onClick = {review = 3}){
                                        Icon(
                                            painter = painterResource(id = if (review > 2) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24),
                                            contentDescription = stringResource(R.string._3_stelle),
                                            tint = Color.Yellow,
                                            modifier = Modifier
                                                .size(70.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(11.dp))
                                    IconButton(onClick = {review = 4}){
                                        Icon(
                                            painter = painterResource(id = if (review > 3 ) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24),
                                            contentDescription = stringResource(R.string._4_stelle),
                                            tint = Color.Yellow,
                                            modifier = Modifier
                                                .size(70.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(11.dp))
                                    IconButton(onClick = {review = 5}){
                                        Icon(
                                            painter = painterResource(id = if (review > 4) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24),
                                            contentDescription = stringResource(R.string._5_stelle),
                                            tint = Color.Yellow,
                                            modifier = Modifier
                                                .size(50.dp)
                                        )
                                    }
                                }
                                var text by rememberSaveable { mutableStateOf("") }
                                TextField(value = text,
                                    placeholder = {
                                        Text(
                                            text = stringResource(R.string.testo_recensione),
                                            fontFamily = fonts,
                                            fontSize = 20.sp
                                        )
                                                  },
                                    onValueChange = { newText -> text = newText },
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .height(100.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = Color.Transparent
                                    ),
                                    textStyle = TextStyle(fontFamily = fonts, fontSize = 20.sp)
                                )
                                IconButton(onClick = {
                                    reviewEnabled = false
                                    if (id != null) {
                                        viewModel.createReviewInFirebase(id, review, text)
                                    }
                                }, modifier = Modifier
                                    .padding(11.dp)
                                    .align(Alignment.CenterHorizontally)){
                                    Icon(painter = painterResource(R.drawable.add_circle_plus_1024x1024), contentDescription = "Aggiungi", tint = BlueText, modifier = Modifier.size(30.dp)
                                    )
                                }
                            }
                        }
                    }
            }
            var currentBookId by rememberSaveable{ mutableStateOf("") }
            var reviewsUpdated : Boolean = viewModel.reviewsUpdated
            if(reviews.first > 0) {
                if(id != null){
                    if(currentBookId != id){
                        currentBookId = id
                        viewModel.reviewsUpdated = false
                        viewModel.reviews.clear()
                        viewModel.getReviewsPlusUser(currentBookId)
                    }
                }

                if(reviewsUpdated){
                    Text(
                        text = stringResource(R.string.recensioni),
                        fontFamily = fonts,
                        fontSize = 20.sp,
                        color = BlueText,
                        textAlign = TextAlign.Center
                    )

                    LazyRow(){
                        items(viewModel.reviews){
                            Box(modifier = Modifier
                                .padding(10.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(BlackBar)
                                .size(200.dp)
                            ){
                                Column (
                                    modifier = Modifier.fillMaxSize()
                                ){
                                    Text(
                                        text = "User : " + it.username,
                                        fontFamily = fonts,
                                        fontSize = 18.sp,
                                        color = WhiteText,
                                        modifier = Modifier
                                            .padding(8.dp)
                                    )
                                    Text(
                                        text = "Voto : " + it.stars,
                                        fontFamily = fonts,
                                        fontSize = 18.sp,
                                        color = WhiteText,
                                        modifier = Modifier
                                            .padding(8.dp)
                                    )
                                    if (it.desc.isNotBlank()) {
                                        Text(
                                            text = "Descrizione : " + it.desc,
                                            fontFamily = fonts,
                                            fontSize = 15.sp,
                                            color = WhiteText,
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .fillMaxSize(),
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }

                            }


                        }
                    }
                }

            }
            else{
                Box(modifier = Modifier
                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.no_recensioni),
                        fontFamily = fonts,
                        fontSize = 20.sp,
                        color = BlueText
                    )
                }

            }


        }
    }
}
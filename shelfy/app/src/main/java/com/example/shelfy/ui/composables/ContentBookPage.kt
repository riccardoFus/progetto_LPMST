package com.example.shelfy.ui.composables

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shelfy.ConnectionState
import com.example.shelfy.MainActivity
import com.example.shelfy.R
import com.example.shelfy.getCurrentConnectivityState
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlackPage
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.WhiteText
import com.example.shelfy.ui.theme.fonts
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun AnimatedBox(text: String) {
    // Define the state variable to manage the visibility of the full text
    var showTrama by remember { mutableStateOf(false) }

    if(text.isNotBlank()){
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(BlackBar)
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clickable { showTrama = !showTrama },
                fontWeight = FontWeight.Normal,
                fontFamily = fonts,
                textAlign = TextAlign.Left,
                overflow = TextOverflow.Ellipsis,
                maxLines = if (showTrama) Int.MAX_VALUE else 7
            )
        }
    }
}

@SuppressLint("SuspiciousIndentation", "CoroutineCreationDuringComposition", "ResourceType")
@Composable
fun ContentBookPage(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier,
    mainActivity: MainActivity
){
    val connectivityManager = mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var showDialog : Boolean by rememberSaveable{ mutableStateOf(false) }
    if(showDialog){
        Dialog(onDismissRequest = { showDialog = false}) {
            Text(
                text = stringResource(R.string.essere_connessi_ad_internet_per_effettuare_questa_operazione),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                fontFamily = fonts,
                modifier = Modifier
                    .width(300.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(BlackBar)
                    .padding(18.dp),
                textAlign = TextAlign.Center,
                color = WhiteText
            )
        }
    }
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

            // get the readlist, useful to show readlists in case of "add book in a readlist"
            if(!viewModel.readlistsUpdated) {
                viewModel.readlistsUpdated = true
                viewModel.getReadlists()
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        "https://" + if((viewModel.bookUiState.data?.volumeInfo?.imageLinks?.large?.isBlank()) != true){
                            viewModel.bookUiState.data?.volumeInfo?.imageLinks?.large?.substring(7)
                        }else if((viewModel.bookUiState.data?.volumeInfo?.imageLinks?.small?.isBlank()) != true){
                            viewModel.bookUiState.data?.volumeInfo?.imageLinks?.small?.substring(7)
                        }else if((viewModel.bookUiState.data?.volumeInfo?.imageLinks?.thumbnail?.isBlank()) != true){
                            viewModel.bookUiState.data?.volumeInfo?.imageLinks?.thumbnail?.substring(7)
                        }else if((viewModel.bookUiState.data?.volumeInfo?.imageLinks?.smallThumbnail?.isBlank()) != true){
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
                            10.dp
                        )
                    ),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.content)
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
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .fillMaxWidth(),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = fonts,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = (viewModel.bookUiState?.data?.volumeInfo?.authors?.toString()?.replace("[", "")?.replace("]", "") ?:
                            stringResource(id = R.string.no_autori)),
                        color = WhiteText,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Light,
                        fontFamily = fonts,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Column (modifier = Modifier.padding(top = 11.dp, start = 11.dp)){
                    // Creates an intent to share the book's info link as plain text
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            stringResource(R.string.ti_consiglio_questo_libro) + viewModel.bookUiState.data?.volumeInfo?.title + "\n"
                            + stringResource(R.string.ho_usato_shelfy_per_consigliartelo_usalo_anche_tu)
                        )
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null);
                    val context = LocalContext.current;

                    // boolean to open a dropdown menu with all the functionalities
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
                    val libreria = stringResource(R.string.libreria)
                    var aggiunto by remember{mutableStateOf(false)}
                    if(more){
                        DropdownMenu(
                            expanded = true,
                            onDismissRequest = { more = false },
                            modifier = Modifier
                                .background(BlackBar)
                        ) {
                            // DropdownMenuItem to share a book
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
                                    Text(text = stringResource(R.string.condividi_libro), color = WhiteText)},
                                onClick = { context.startActivity(shareIntent) },
                                modifier = Modifier
                                    .height(25.dp)
                            )
                            Spacer(modifier = Modifier
                                .size(10.dp))
                            // DropdownMenuItem to visualize and modify a note
                            DropdownMenuItem(leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_sticky_note_2_24),
                                    contentDescription = stringResource(R.string.visualizza_nota),
                                    tint = BlueText,
                                    modifier = Modifier
                                        .weight(1f)
                                        .size(25.dp)
                                )
                            },
                                text = {
                                    Text(text = stringResource(R.string.visualizza_nota), color = WhiteText)},
                                onClick = {
                                    if(getCurrentConnectivityState(connectivityManager) == ConnectionState.Available){
                                        noteVisualizer = true
                                        viewModel.getNota(viewModel.userId, id)
                                    }else{
                                        showDialog = true
                                    }
                                          },
                                modifier = Modifier
                                    .height(25.dp)
                            )
                            Spacer(modifier = Modifier
                                .size(10.dp))
                            // DropdownMenuItem to add a book in the library
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
                                    Text(text = stringResource(id = R.string.aggiungi_alla_libreria), color = WhiteText)},
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
                                    if(getCurrentConnectivityState(connectivityManager) == ConnectionState.Available){
                                        viewModel.addToReadlist(
                                            viewModel.bookUiState.data,
                                            viewModel.userId,
                                            libreria
                                        )
                                        GlobalScope.launch {
                                            aggiunto = true
                                            Thread.sleep(2000)
                                            aggiunto = false
                                        }
                                    }else{
                                        showDialog = true
                                    }
                                },
                                modifier = Modifier
                                    .height(25.dp)

                            )
                            Spacer(modifier = Modifier
                                .size(10.dp))
                            // DropdownMenuItem to add a book in a certain readlist
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
                                    Text(text = stringResource(id = R.string.aggiungi_a_una_readlist), color = WhiteText)},
                                onClick = {
                                    if(getCurrentConnectivityState(connectivityManager) == ConnectionState.Available){
                                        showReadlists = true
                                    }else{
                                        showDialog = true
                                    }
                                },
                                modifier = Modifier
                                    .height(25.dp)
                            )
                            Spacer(modifier = Modifier
                                .size(10.dp))
                            // DropdownMenuItem to add a review
                            DropdownMenuItem(leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_edit_24),
                                    contentDescription = stringResource(R.string.aggiungi_una_recensione),
                                    tint = BlueText,
                                    modifier = Modifier
                                        .weight(1f)
                                        .size(25.dp)
                                )
                            },
                                text = {
                                    Text(text = stringResource(id = R.string.aggiungi_una_recensione), color = WhiteText)},
                                onClick = {
                                    if(getCurrentConnectivityState(connectivityManager) == ConnectionState.Available){
                                        more = false
                                        reviewEnabled = true
                                    }else{
                                        showDialog = true
                                    }
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
            var maxChar = 256
            testo = note
            var charInsertedNote by rememberSaveable {
                mutableStateOf(testo.length)
            }
                if (noteVisualizer) {
                    var noteAlreadyExists = viewModel.noteAlreadyExists
                    // checks if a given user have already a note about a certain book
                    viewModel.checkNoteAlreadyInserted(viewModel.userId, id!!)
                    // dialog that shows the note, useful to change value of the note or only read
                    Dialog(onDismissRequest = { noteVisualizer = false }) {
                        TextField(
                            singleLine = false,
                            value = testo,
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.nessuna_nota) ,
                                    fontFamily = fonts,
                                    fontSize = 20.sp
                                )
                            },
                            onValueChange = {newText ->
                                testo = newText.take(maxChar)
                                charInsertedNote = testo.length
                                            },
                            textStyle = TextStyle(fontFamily = fonts, fontSize = 20.sp, color = WhiteText),
                            modifier = Modifier
                                .width(450.dp)
                                .height(450.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                unfocusedTextColor = WhiteText,
                                unfocusedLabelColor = WhiteText,
                                unfocusedLeadingIconColor = WhiteText,
                                focusedTextColor = WhiteText,
                                focusedLabelColor = WhiteText,
                                focusedLeadingIconColor = WhiteText,
                                cursorColor = WhiteText,
                                unfocusedPlaceholderColor = WhiteText,
                                focusedPlaceholderColor = WhiteText,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedSupportingTextColor = WhiteText,
                                unfocusedSupportingTextColor = WhiteText,
                                focusedPrefixColor = WhiteText,
                                unfocusedPrefixColor = WhiteText,
                                focusedSuffixColor = WhiteText,
                                unfocusedSuffixColor = WhiteText,
                                focusedContainerColor = BlackBar,
                                unfocusedContainerColor = BlackBar
                            ),
                            trailingIcon = {
                                Column(modifier = Modifier.fillMaxHeight()) {
                                    IconButton(onClick = {
                                        noteVisualizer = false
                                        charInsertedNote = note.length
                                    },
                                        modifier = Modifier
                                            .padding(10.dp)) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_close_24),
                                            contentDescription = stringResource(R.string.aggiungi_una_nota),
                                            modifier = Modifier
                                                .size(50.dp)
                                        )
                                    }
                                    IconButton(onClick = {
                                        noteVisualizer = false
                                        if(noteAlreadyExists)
                                            viewModel.updateNote(viewModel.userId, testo, id!!)
                                        else
                                            viewModel.addNote(viewModel.userId, testo, id!!)
                                    },
                                        modifier = Modifier
                                            .padding(10.dp)) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_done_24),
                                            contentDescription = stringResource(R.string.aggiungi_una_nota),
                                            modifier = Modifier
                                                .size(50.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(270.dp))
                                    Text(text = "" + charInsertedNote + "/" + maxChar,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp,
                                        fontFamily = fonts,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(end = 4.dp),
                                        color = BlueText)
                                }
                            })

                    }
                }

            // boolean to extend or not the length of the container that contains the plot
            // of the book
            var showTrama by rememberSaveable { mutableStateOf(false) }
            // sometimes, Google gives desc with html tag, in this way remove all the tags
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
            AnimatedBox(text)

            // dropdown menu that shows all the readlist where a user can insert a certain book
            if(showReadlists) {
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = {showReadlists = false},
                    modifier = Modifier
                        .background(BlackBar)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .height(250.dp)
                            .width(165.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                    ) {
                        items(viewModel.readlists) { item ->
                            Column(modifier = Modifier
                                .padding(5.dp)
                                .align(Alignment.CenterHorizontally)){
                                OutlinedButton(
                                    onClick = {
                                        viewModel.addToReadlist(viewModel.bookUiState.data, viewModel.userId, item.name)
                                        showReadlists = false
                                              },
                                    content = {
                                        Text(
                                            text = item.name,
                                            color = BlueText,
                                            fontFamily = fonts,
                                            fontSize = 15.sp,
                                            textAlign = TextAlign.Justify
                                        )
                                              },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(10.dp)
                                )
                            }
                        }
                    }
                }
            }

            var reviews = viewModel.numberAndMediaReviews
            if(id != null) {
                viewModel.getReviews(id)
            }
            var charInsertedReview by rememberSaveable {
                mutableStateOf(0)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.TopStart
            ) {
                // dialog to insert a new review for a certain book
                    if (reviewEnabled) {
                        Dialog(onDismissRequest = { reviewEnabled = false; review = 0 }) {
                            Column(modifier = Modifier
                                .background(BlackPage)
                                .width(400.dp)
                                .height(350.dp)
                                .border(
                                    width = 3.dp,
                                    color = BlackBar,
                                    shape = RoundedCornerShape(10.dp)
                                ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
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
                                    onValueChange = { newText ->
                                        text = newText.take(maxChar)
                                        charInsertedReview = text.length
                                                    },
                                    textStyle = TextStyle(fontFamily = fonts, fontSize = 20.sp, color = WhiteText),
                                    modifier = Modifier
                                        .width(450.dp)
                                        .height(175.dp)
                                        .padding(20.dp),
                                    trailingIcon = {
                                        Text(text = "" + charInsertedReview + "/" + maxChar,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp,
                                            fontFamily = fonts,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.padding(end = 4.dp),
                                            color = BlueText)
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = TextFieldDefaults.colors(
                                        unfocusedTextColor = WhiteText,
                                        unfocusedLabelColor = WhiteText,
                                        unfocusedLeadingIconColor = WhiteText,
                                        focusedTextColor = WhiteText,
                                        focusedLabelColor = WhiteText,
                                        focusedLeadingIconColor = WhiteText,
                                        cursorColor = WhiteText,
                                        unfocusedPlaceholderColor = WhiteText,
                                        focusedPlaceholderColor = WhiteText,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        focusedSupportingTextColor = WhiteText,
                                        unfocusedSupportingTextColor = WhiteText,
                                        focusedPrefixColor = WhiteText,
                                        unfocusedPrefixColor = WhiteText,
                                        focusedSuffixColor = WhiteText,
                                        unfocusedSuffixColor = WhiteText,
                                        focusedContainerColor = BlackBar,
                                        unfocusedContainerColor = BlackBar
                                    ),
                                )
                                OutlinedButton(onClick = {
                                    reviewEnabled = false
                                    if (id != null) {
                                        viewModel.createReviewInFirebase(id, review, text)
                                        // viewModel.reviews.clear()
                                        viewModel.getReviews(viewModel.bookUiState.data!!.id)
                                        review = 0
                                        charInsertedReview = 0
                                    }
                                },
                                    modifier = Modifier
                                        .widthIn(185.dp)
                                        .padding(10.dp),
                                    content = {
                                        Text(text = stringResource(R.string.aggiungi_recensione), modifier = Modifier
                                            .align(Alignment.CenterVertically),
                                            fontSize = 19.sp,
                                            fontFamily = fonts,
                                            textAlign = TextAlign.Center,
                                            color = WhiteText
                                        )
                                    },
                                    border = BorderStroke(3.dp, BlackBar)
                                )
                            }
                        }
                    }
            }
            var showReviews by rememberSaveable {
                mutableStateOf(false)
            }
            // button that shows or not the reviews about a book
            OutlinedButton(onClick = { showReviews = !showReviews },
                modifier = Modifier
                    .padding(20.dp)
                    .widthIn(120.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent
                ),
                border = BorderStroke(1.dp, color = BlueText),
                content = {
                    Text(text = if(showReviews) stringResource(R.string.nascondi_recensioni) else stringResource(
                        R.string.visualizza_recensioni
                    ), modifier = Modifier
                        .align(Alignment.CenterVertically),
                        fontSize = 20.sp,
                        fontFamily = fonts,  color = WhiteText,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start)
                }
            )
            if(showReviews){
                viewModel.getReviewsPlusUser(viewModel.bookUiState.data!!.id)
                if(reviews.first > 0) {
                    Text(
                        text = stringResource(R.string.recensioni),
                        fontFamily = fonts,
                        fontSize = 20.sp,
                        color = BlueText,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.numero_voti) + reviews.first.toString() + stringResource(
                            R.string.media_voti
                        ) + reviews.second.toString(), color = WhiteText, fontSize = 16.sp,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(), fontWeight = FontWeight.SemiBold, fontFamily = fonts,
                        textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis
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
                                        text = "User: " + it.username,
                                        fontFamily = fonts,
                                        fontSize = 18.sp,
                                        color = WhiteText,
                                        modifier = Modifier
                                            .padding(8.dp)
                                    )
                                    Row(
                                        modifier = Modifier
                                            .padding(8.dp)
                                    ) {
                                        for (i in 1..it.stars) {
                                            Icon(painter = painterResource(R.drawable.baseline_star_24),
                                                contentDescription = "Stelle",
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .align(Alignment.CenterVertically),
                                                tint = Color.Yellow)
                                        }
                                        for(i in (1..5-it.stars)){
                                            Icon(painter = painterResource(R.drawable.baseline_star_24),
                                                contentDescription = "Stelle",
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .align(Alignment.CenterVertically),
                                                tint = BlackPage)
                                        }
                                    }
                                    if (it.desc.isNotBlank()) {
                                        Column(
                                            modifier = Modifier
                                                .verticalScroll(rememberScrollState())
                                        ) {
                                            Text(
                                                text = it.desc,
                                                fontFamily = fonts,
                                                fontSize = 15.sp,
                                                color = WhiteText,
                                                fontWeight = FontWeight.Light,
                                                modifier = Modifier
                                                    .padding(8.dp)
                                                    .fillMaxSize()
                                            )
                                        }

                                    }
                                }

                            }


                        }
                    }
                }else{
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
}
package com.example.shelfy.ui.composables

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shelfy.R
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.theme.BlackPage
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.WhiteText
import com.example.shelfy.ui.theme.fonts

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
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        ("https://" + (viewModel.bookUiState.data?.volumeInfo?.imageLinks?.thumbnail?.substring(
                            7
                        )
                            ?: stringResource(id = R.string.book_image_no_available)))
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
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = (viewModel.bookUiState.data?.volumeInfo?.title ?: stringResource(id = R.string.no_titolo)),
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
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .fillMaxWidth(),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = fonts,
                        textAlign = TextAlign.Left,
                        overflow = TextOverflow.Ellipsis
                    )
                }
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
                IconButton(onClick = { context.startActivity(shareIntent) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.share_1024x896),
                        contentDescription = stringResource(R.string.share),
                        tint = BlueText,
                        modifier = Modifier
                            .weight(1f)
                            .size(25.dp)
                    )
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
            var id = viewModel.bookUiState.data?.id
            var reviews = viewModel.numberAndMediaReviews
            if(id != null) {
                viewModel.getReviews(id)
            }
            Text(
                text = reviews.first.toString() + "-" + reviews.second.toString(), color = WhiteText, fontSize = 16.sp,
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
                Column(
                ) {
                    Row() {
                        IconButton(
                            onClick = {},
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_circle_plus_1024x1024),
                                contentDescription = stringResource(R.string.aggiungi_libro),
                                tint = BlueText,
                                modifier = Modifier
                                    .size(32.dp)
                            )
                        }
                        Text(
                            text = AnnotatedString(stringResource(R.string.aggiungi_alla_libreria)),
                            style = TextStyle(
                                fontFamily = fonts,
                                fontSize = 20.sp,
                                color = BlueText
                            ),
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(7.dp)
                        )
                    }
                    Row() {
                        IconButton(
                            onClick = {},
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_circle_plus_1024x1024),
                                contentDescription = stringResource(id = R.string.aggiungi_libro),
                                tint = BlueText,
                                modifier = Modifier
                                    .size(32.dp)
                            )
                        }
                        Text(
                            text = AnnotatedString(stringResource(R.string.aggiungi_a_una_readlist)),
                            style = TextStyle(
                                fontFamily = fonts,
                                fontSize = 20.sp,
                                color = BlueText
                            ),
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(7.dp)
                        )
                    }
                    var noteEnabled by rememberSaveable { mutableStateOf(false) }

                    Row() {
                        IconButton(
                            onClick = {
                                noteEnabled = true
                            },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_circle_plus_1024x1024),
                                contentDescription = stringResource(R.string.aggiungi_una_nota),
                                tint = BlueText,
                                modifier = Modifier
                                    .size(32.dp)
                            )
                        }
                        ClickableText(
                            text = AnnotatedString(stringResource(R.string.aggiungi_una_nota)),
                            onClick = {
                                noteEnabled = true
                                      },
                            style = TextStyle(
                                fontFamily = fonts,
                                fontSize = 20.sp,
                                color = BlueText,
                                textAlign = TextAlign.Justify,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(7.dp)
                        )
                    }
                    var note by rememberSaveable { mutableStateOf("") }
                    if (noteEnabled) {
                        Dialog(onDismissRequest = {noteEnabled = false}) {
                            TextField(singleLine = false,
                                value = note,
                                placeholder = {
                                    Text(
                                        text = stringResource(R.string.aggiungi_una_nota),
                                        fontFamily = fonts,
                                        fontSize = 20.sp
                                    )
                                },
                                onValueChange = { newText -> note = newText },
                                textStyle = TextStyle(fontFamily = fonts, fontSize = 20.sp),
                                modifier = Modifier
                                    .height(100.dp),
                                shape = RoundedCornerShape(30.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                ),
                                trailingIcon = {
                                    IconButton(onClick = { noteEnabled = false; viewModel.addNota(viewModel.getUser(), note, id!!) }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.add_circle_plus_1024x1024),
                                            contentDescription = stringResource(R.string.aggiungi_una_nota),
                                            tint = BlueText,
                                            modifier = Modifier
                                                .size(30.dp)
                                        )
                                    }
                                })
                        }
                    }
                    var reviewEnabled by rememberSaveable { mutableStateOf(false) }
                    var review by rememberSaveable { mutableStateOf(0) }
                    if (reviewEnabled) {
                        Dialog(onDismissRequest = { reviewEnabled = false }) {
                            Column(modifier = Modifier
                                .background(Color.Transparent)
                                .width(300.dp)
                                .height(500.dp)
                                .fillMaxWidth()){
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
                    Row(){
                        IconButton(
                            onClick = {
                                      reviewEnabled = true
                            },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_circle_plus_1024x1024),
                                contentDescription = stringResource(R.string.aggiungi_una_recensione),
                                tint = BlueText,
                                modifier = Modifier
                                    .size(32.dp)
                            )
                        }
                        ClickableText(
                            text = AnnotatedString(stringResource(R.string.aggiungi_una_recensione)),
                            onClick = {reviewEnabled = true},
                            style = TextStyle(
                                fontFamily = fonts,
                                fontSize = 20.sp,
                                color = BlueText,
                                textAlign = TextAlign.Justify,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(7.dp)
                        )
                    }
                }
            }
        }
    }
}
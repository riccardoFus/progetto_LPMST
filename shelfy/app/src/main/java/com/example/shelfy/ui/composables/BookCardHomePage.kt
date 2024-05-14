package com.example.shelfy.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shelfy.R
import com.example.shelfy.data.remote.responses.Item
import com.example.shelfy.navigation.Screens
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.WhiteText
import com.example.shelfy.ui.theme.fonts

@Composable
fun BookCardHomePage(
    item: Item,
    viewModel : AppViewModel,
    navController : NavHostController,
    modifier : Modifier = Modifier,
    page : String,
    readlist: String
){
    Column(
        modifier = Modifier
            .width(180.dp)
            .padding(
                if (page == "profile") {
                    8.dp
                } else {
                    0.dp
                }
            )
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (page != "profile") {
                    Color.Transparent
                } else {
                    BlackBar
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    "https://" + if ((item?.volumeInfo?.imageLinks?.large?.length ?: 0) > 0) {
                        item?.volumeInfo?.imageLinks?.large?.substring(7)
                    } else if ((item?.volumeInfo?.imageLinks?.small?.length ?: 0) > 0) {
                        item?.volumeInfo?.imageLinks?.small?.substring(7)
                    } else if ((item?.volumeInfo?.imageLinks?.thumbnail?.length ?: 0) > 0) {
                        item?.volumeInfo?.imageLinks?.thumbnail?.substring(7)
                    } else if ((item?.volumeInfo?.imageLinks?.smallThumbnail?.length ?: 0) > 0) {
                        item?.volumeInfo?.imageLinks?.smallThumbnail?.substring(7)
                    } else {
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
                )
                .clickable(
                    onClick = {
                        viewModel.getBook(item?.id.toString())
                        navController.navigate(Screens.VISUALIZER_SCREEN)
                    }
                ),
            contentScale = ContentScale.Crop
        )
        Text(
            text = (item?.volumeInfo?.title ?: stringResource(R.string.no_titolo)),
            color = WhiteText,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 2.dp),
            fontWeight = FontWeight.SemiBold,
            fontFamily = fonts,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        if (page == "profile") {
            Row {
                TextButton(onClick = { viewModel.deleteBookFromReadlist(readlist, item.id) }) {
                    Text(
                        text = "Rimuovi da $readlist",
                        fontFamily = fonts,
                        fontSize = 15.sp,
                        color = BlueText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }

            }

        }
    }
}
package com.example.shelfy.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.WhiteText
import com.example.shelfy.ui.theme.fonts

@Composable
fun BookCardHomePage(
    item: Item,
    viewModel : AppViewModel,
    navController : NavHostController,
    modifier : Modifier = Modifier,
    page : String
){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(
                "https://" + if((item?.volumeInfo?.imageLinks?.large?.length ?: 0) != 0){
                    item?.volumeInfo?.imageLinks?.large?.substring(7)?.replace("zoom=1", "zoom=0")
                }else if((item?.volumeInfo?.imageLinks?.small?.length ?: 0) != 0){
                    item?.volumeInfo?.imageLinks?.small?.substring(7)?.replace("zoom=1", "zoom=0")
                }else if((item?.volumeInfo?.imageLinks?.thumbnail?.length ?: 0) != 0){
                    item?.volumeInfo?.imageLinks?.thumbnail?.substring(7)?.replace("zoom=1", "zoom=0")
                }else if((item?.volumeInfo?.imageLinks?.smallThumbnail?.length ?: 0) != 0){
                    item?.volumeInfo?.imageLinks?.smallThumbnail?.substring(7)?.replace("zoom=1", "zoom=0")
                }else{
                stringResource(R.string.book_image_no_available)
                }
            )
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier
            .height(230.dp)
            .width(130.dp)
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
        overflow = TextOverflow.Ellipsis
    )
    if(page == "profile") {
        IconButton(onClick = { viewModel.deleteBookFromReadlist("Libreria", item.id) }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_remove_circle_outline_24),
                contentDescription = "Rimuovi libro",
                tint = BlueText,
                modifier = Modifier
                    .size(23.dp)
            )
        }
    }
}
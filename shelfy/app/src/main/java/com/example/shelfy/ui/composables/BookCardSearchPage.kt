package com.example.shelfy.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.shelfy.ui.theme.WhiteText
import com.example.shelfy.ui.theme.fonts

@Composable
fun BookCardSearchPage(
    item : Item,
    viewModel : AppViewModel,
    navController : NavHostController,
    modifier : Modifier = Modifier
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
                }else{
                    item?.volumeInfo?.imageLinks?.smallThumbnail?.substring(7)?.replace("zoom=1", "zoom=0")
                }
            )
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(180.dp)
            .height(250.dp)
            .clip(
                RoundedCornerShape(
                    8.dp
                )
            )
            .clickable(onClick = {
                viewModel.getBook(item?.id.toString())
                navController.navigate(Screens.VISUALIZER_SCREEN)
            })
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp))
    {
        Text(
            text = (item?.volumeInfo?.title ?: stringResource(id = R.string.no_titolo)),
            color = WhiteText,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(top = 2.dp),
            fontWeight = FontWeight.SemiBold,
            fontFamily = fonts
        )
        Text(
            text = (item?.volumeInfo?.authors ?: stringResource(R.string.no_autori)).toString()
                .replace("[", "").replace("]", ""),
            color = WhiteText,
            fontSize = 13.sp,
            modifier = Modifier
                .padding(top = 2.dp),
            fontWeight = FontWeight.SemiBold,
            fontFamily = fonts
        )
        Text(
            text = "Trama : " + (item?.volumeInfo?.description ?: stringResource(R.string.trama_non_presente)),
            color = WhiteText,
            fontSize = 11.sp,
            modifier = Modifier
                .padding(top = 2.dp),
            fontWeight = FontWeight.SemiBold,
            fontFamily = fonts,
            overflow = TextOverflow.Ellipsis)
    }
}
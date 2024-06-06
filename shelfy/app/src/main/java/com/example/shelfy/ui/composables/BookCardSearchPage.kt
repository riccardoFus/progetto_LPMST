package com.example.shelfy.ui.composables

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shelfy.ConnectionState
import com.example.shelfy.MainActivity
import com.example.shelfy.R
import com.example.shelfy.data.remote.responses.Item
import com.example.shelfy.getCurrentConnectivityState
import com.example.shelfy.navigation.Screens
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.WhiteText
import com.example.shelfy.ui.theme.fonts
import com.example.shelfy.util.Resource

@Composable
fun BookCardSearchPage(
    item : Item,
    viewModel : AppViewModel,
    navController : NavHostController,
    mainActivity : MainActivity,
    modifier : Modifier = Modifier,
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
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(
                "https://" + if((item?.volumeInfo?.imageLinks?.large?.isBlank()) != true){
                    item?.volumeInfo?.imageLinks?.large?.substring(7)
                }else if((item?.volumeInfo?.imageLinks?.small?.isBlank()) != true){
                    item?.volumeInfo?.imageLinks?.small?.substring(7)
                }else if((item?.volumeInfo?.imageLinks?.thumbnail?.isBlank()) != true){
                    item?.volumeInfo?.imageLinks?.thumbnail?.substring(7)
                }else if((item?.volumeInfo?.imageLinks?.smallThumbnail?.isBlank()) != true){
                    item?.volumeInfo?.imageLinks?.smallThumbnail?.substring(7)
                }else{
                    stringResource(R.string.book_image_no_available)
                }
            )
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(125.dp)
            .height(185.dp)
            .clip(
                RoundedCornerShape(
                    10.dp
                )
            )
            .clickable(onClick = {
                viewModel.bookUiState = Resource.Loading<Item>()
                if (getCurrentConnectivityState(connectivityManager) == ConnectionState.Available) {
                    viewModel.getBook(item?.id.toString())
                    navController.navigate(Screens.VISUALIZER_SCREEN)
                } else {
                    showDialog = true
                }
            }),
        placeholder = painterResource(id = R.drawable.content)
    )
    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable {
                viewModel.bookUiState = Resource.Loading<Item>()
                if (getCurrentConnectivityState(connectivityManager) == ConnectionState.Available) {
                    viewModel.getBook(item?.id.toString())
                    navController.navigate(Screens.VISUALIZER_SCREEN)
                } else {
                    showDialog = true
                }
            }
            )
    {
        Text(
            text = (item?.volumeInfo?.title ?: stringResource(id = R.string.no_titolo)),
            color = WhiteText,
            fontSize = 20.sp,
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
                .padding(top = 2.dp, bottom = 10.dp),
            fontWeight = FontWeight.Light,
            fontFamily = fonts,
            maxLines = 2
        )
        Text(
            text = (item?.volumeInfo?.description ?: stringResource(R.string.trama_non_presente)),
            color = WhiteText,
            fontSize = 11.sp,
            modifier = Modifier
                .padding(top = 2.dp),
            fontWeight = FontWeight.Normal,
            fontFamily = fonts,
            overflow = TextOverflow.Ellipsis,
            maxLines = 7)
    }
}

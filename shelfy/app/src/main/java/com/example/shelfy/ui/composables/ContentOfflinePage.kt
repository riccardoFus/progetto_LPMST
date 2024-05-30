package com.example.shelfy.ui.composables

import android.content.Context
import android.net.ConnectivityManager
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shelfy.ConnectionState
import com.example.shelfy.MainActivity
import com.example.shelfy.R
import com.example.shelfy.ShelfyApp
import com.example.shelfy.getCurrentConnectivityState
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.fonts
import com.google.firebase.FirebaseApp

@Composable
fun ContentOfflinePage(
    mainActivity: MainActivity,
    modifier : Modifier = Modifier
){
    // check if a user is connected online
    val connectivityManager = mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier
            .size(200.dp))
        Icon(
            painter = painterResource(id = R.drawable._3959),
            contentDescription = stringResource(R.string.home),
            tint = BlueText,
            modifier = Modifier
                .size(100.dp)
        )
        Text(
            text = stringResource(R.string.attivare_connessione_internet_per_utilizzare_shelfy),
            color = BlueText,
            fontFamily = fonts,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(50.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        // check if user is returned online or not
        OutlinedButton(onClick = {
            if(getCurrentConnectivityState(connectivityManager) == ConnectionState.Available) {
                mainActivity.setContent {
                    FirebaseApp.initializeApp(mainActivity)
                    ShelfyApp()
                }
            }
        },
            modifier = Modifier
                .widthIn(185.dp),
            content = {
                Text(text = stringResource(R.string.riprova), modifier = Modifier
                    .align(Alignment.CenterVertically),
                    fontSize = 19.sp,
                    fontFamily = fonts,
                    textAlign = TextAlign.Center,
                    color = BlueText
                )
            },
            border = BorderStroke(1.dp, BlueText)
        )
    }
}
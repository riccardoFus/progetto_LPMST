package com.example.shelfy

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.shelfy.navigation.NavGraph
import com.example.shelfy.ui.composables.TopBar
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlackPage
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.fonts
import com.google.firebase.FirebaseApp

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}

private fun getCurrentConnectivityState(
    connectivityManager: ConnectivityManager
): ConnectionState {
    val connected = connectivityManager.allNetworks.any { network ->
        connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }

    return if (connected) ConnectionState.Available else ConnectionState.Unavailable
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var connection = getCurrentConnectivityState(connectivityManager)
        if(connection == ConnectionState.Available){
            setContent {
                FirebaseApp.initializeApp(this)
                ShelfyApp()
            }
        }else{
            setContent {
                ShelfyAppNoInternet(this)
            }
        }
    }

    @Composable
    fun ShelfyApp(){
        NavGraph()
    }

    @Composable
    fun ShelfyAppNoInternet(mainActivity: MainActivity) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BlackPage),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            TopBar(
                modifier = Modifier
                    .weight(0.9f)
                    .background(color = BlackBar)
            )
            Column(
                modifier = Modifier
                    .weight(7f),
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
                OutlinedButton(onClick = {
                    if(getCurrentConnectivityState(connectivityManager) == ConnectionState.Available) {
                        mainActivity.setContent {
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
    }
}

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
import com.example.shelfy.ui.screens.OfflinePage
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlackPage
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.fonts
import com.google.firebase.FirebaseApp

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}

// Retrieves the current state of the network connectivity.
// This function checks all available networks using the provided `ConnectivityManager` and determines
// if there is any network with internet capability. If at least one network is found with internet
// capability, the function returns `ConnectionState.Available`; otherwise, it returns `ConnectionState.Unavailable`.
fun getCurrentConnectivityState(
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
        // Checks the current network connection state and navigates to the appropriate page based on the connectivity:
        // If the connection is available (ON), navigate to the home page if the user is logged in,
        // or the login page if the user is not logged in.
        // If the connection is unavailable (OFF), navigate to the offline page.
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var connection = getCurrentConnectivityState(connectivityManager)
        if(connection == ConnectionState.Available){
            setContent {
                FirebaseApp.initializeApp(this)
                ShelfyApp(this)
            }
        }else{
            setContent {
                ShelfyAppNoInternet(this)
            }
        }
    }

}
@Composable
fun ShelfyApp(mainActivity: MainActivity){
    NavGraph(mainActivity = mainActivity)
}

@Composable
fun ShelfyAppNoInternet(mainActivity: MainActivity) {
    OfflinePage(mainActivity)
}

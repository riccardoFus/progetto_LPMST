package com.example.shelfy.ui.composables

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.shelfy.MainActivity
import com.example.shelfy.R
import com.example.shelfy.navigation.Screens
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.WhiteText
import com.example.shelfy.ui.theme.fonts

@Composable
fun ContentFunctionPage(
    viewModel : AppViewModel,
    navController : NavHostController,
    mainActivity : MainActivity,
    modifier : Modifier = Modifier
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
    Box(modifier = modifier){
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(220.dp))
            OutlinedButton(onClick = {
            },
                modifier = Modifier
                    .padding(10.dp)
                    .widthIn(120.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, color = BlueText),
                content = {
                    Text(text = stringResource(R.string.visualizza_recensioni_personali), modifier = Modifier
                        .align(Alignment.CenterVertically),
                        fontSize = 20.sp,
                        fontFamily = fonts,  color = WhiteText,
                        textAlign = TextAlign.Center)
                },
                enabled = false
            )
            OutlinedButton(onClick = {
            },
                modifier = Modifier
                    .padding(10.dp)
                    .widthIn(120.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, color = BlueText),
                content = {
                    Text(text = stringResource(R.string.visualizza_note_personali), modifier = Modifier
                        .align(Alignment.CenterVertically),
                        fontSize = 20.sp,
                        fontFamily = fonts,  color = WhiteText,
                        textAlign = TextAlign.Center)
                }, enabled = false
            )
            OutlinedButton(onClick = {
                viewModel.logout()
                navController.navigate(Screens.LOGIN_SCREEN)
            },
                modifier = Modifier
                    .padding(10.dp)
                    .widthIn(120.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, color = BlueText),
                content = {
                    Text(text = stringResource(R.string.sign_out), modifier = Modifier
                        .align(Alignment.CenterVertically),
                        fontSize = 20.sp,
                        fontFamily = fonts,  color = WhiteText,
                        textAlign = TextAlign.Center)
                }
            )
            OutlinedButton(onClick = {
                viewModel.deleteUser(navController)
            },
                modifier = Modifier
                    .padding(10.dp)
                    .widthIn(120.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, color = Color.Red),
                content = {
                    Text(text = stringResource(R.string.cancella_account), modifier = Modifier
                        .align(Alignment.CenterVertically),
                        fontSize = 20.sp,
                        fontFamily = fonts,  color = WhiteText,
                        textAlign = TextAlign.Center)
                }
            )

        }
    }
}
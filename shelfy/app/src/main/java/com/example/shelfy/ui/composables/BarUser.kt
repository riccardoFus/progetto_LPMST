package com.example.shelfy.ui.composables

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.shelfy.ConnectionState
import com.example.shelfy.MainActivity
import com.example.shelfy.R
import com.example.shelfy.getCurrentConnectivityState
import com.example.shelfy.navigation.Screens
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.WhiteText
import com.example.shelfy.ui.theme.fonts

@Composable
fun BarUser(
    viewModel: AppViewModel,
    navController : NavHostController,
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
    if(viewModel.alreadyReadlistExist){
        Dialog(onDismissRequest = { viewModel.alreadyReadlistExist = false}) {
            Text(
                text = stringResource(R.string.nome_di_readlist_gi_esistente),
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
    Column {
        Row (
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = stringResource(R.string.la_tua_libreria),
                fontFamily = fonts,
                fontSize = 24.sp,
                color = BlueText,
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(65.dp))
            var addReadlistNew by remember { mutableStateOf(false) }
            var sortBy by remember { mutableStateOf("cre") }
            // IconButton to add a new readlist
            IconButton(
                onClick = {
                    if(getCurrentConnectivityState(connectivityManager) == ConnectionState.Available){
                        addReadlistNew = true
                    }else {
                        showDialog = true
                    }
                }
            ){
                Icon(
                    painter = painterResource(id = R.drawable.add_circle_plus_1024x1024),
                    contentDescription = stringResource(R.string.aggiungi_libro),
                    tint = BlueText,
                    modifier = Modifier
                        .size(28.dp))

            }
            // IconButton to sort readlists in ascending or descending
            IconButton(
                onClick = {viewModel.sortReadlists(sortBy); if(sortBy == "cre") sortBy = "dec" else sortBy = "cre"}
            ){
                Icon(
                    painter = painterResource(id = R.drawable.sort_ascending_1024x1024),
                    contentDescription = stringResource(R.string.ordina),
                    tint = BlueText,
                    modifier = Modifier
                        .size(28.dp))

            }
            // IconButton to logout user
            IconButton(onClick = {
                viewModel.logout()
                navController.navigate(Screens.LOGIN_SCREEN)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_exit_to_app_24),
                    contentDescription = stringResource(R.string.esci),
                    tint = BlueText,
                    modifier = Modifier
                        .size(28.dp))
            }
            val libreria = stringResource(id = R.string.libreria)
            var readList by remember {mutableStateOf("")}
            val maxChar = 25
            // if IconButton of addReadlist is clicked, it opens a dialog
            // in this dialog, you can write the name of the new readlist
            // in this dialog, you can write the name of the new readlist
            if (addReadlistNew) {
                Dialog(onDismissRequest = { addReadlistNew = false }) {
                    if(addReadlistNew){
                        Dialog(
                            onDismissRequest = { addReadlistNew = false}
                        ) {
                            OutlinedTextField(
                                singleLine = true,
                                value = readList,
                                placeholder = {
                                    Text(
                                        text = stringResource(R.string.aggiungi_readlist),
                                        fontFamily = fonts,
                                        fontSize = 20.sp
                                    )
                                },
                                onValueChange = {
                                        newText -> readList = newText.take(maxChar)
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(BlackBar)
                                    .padding(8.dp)
                                    .widthIn(400.dp),
                                textStyle = TextStyle(
                                    fontFamily = fonts,
                                    fontSize = 20.sp
                                ),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        if(!readList.isBlank()){
                                            viewModel.addReadlist(readList, viewModel.userId)
                                            addReadlistNew = false
                                            readList = ""
                                        }
                                    }),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedTextColor = BlueText,
                                    unfocusedBorderColor = BlueText,
                                    unfocusedLabelColor = BlueText,
                                    unfocusedLeadingIconColor = BlueText,
                                    focusedTextColor = BlueText,
                                    focusedBorderColor = BlueText,
                                    focusedLabelColor = BlueText,
                                    focusedLeadingIconColor = BlueText,
                                    cursorColor = BlueText,
                                    unfocusedPlaceholderColor = BlueText,
                                    focusedPlaceholderColor = BlueText
                                )
                            )

                        }
                    }
                }
            }
        }
        TextButton(onClick = { viewModel.deleteUser(navController) }) {
            Text(
                text = stringResource(R.string.cancella_account),
                fontFamily = fonts,
                fontSize = 20.sp,
                color = BlueText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp, bottom = 0.dp)
                    .weight(1f),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
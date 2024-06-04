package com.example.shelfy.ui.composables

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.shelfy.ConnectionState
import com.example.shelfy.MainActivity
import com.example.shelfy.R
import com.example.shelfy.getCurrentConnectivityState
import com.example.shelfy.navigation.Screens
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.isValidEmail
import com.example.shelfy.ui.isValidPassword
import com.example.shelfy.ui.sha256
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.WhiteText
import com.example.shelfy.ui.theme.fonts

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContentLoginPage(
    viewModel : AppViewModel,
    navController : NavHostController,
    mainActivity: MainActivity,
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
                textAlign = TextAlign.Center
            )
        }
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally){

            Text(
                text = stringResource(id = R.string.login),
                color = BlueText,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 11.dp),
                fontSize = 40.sp,
                fontFamily = fonts
            )
            var email by rememberSaveable { mutableStateOf("") }
            var emailCorrect by rememberSaveable { mutableStateOf(true) }
            Box(modifier = Modifier.padding(top = 15.dp)) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                            newText -> email = newText
                        // passwordCorrect = isValidPassword(password)
                    },
                    placeholder = { Text(text = stringResource(R.string.email)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 20.sp, color = BlueText),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.email_icon_126),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp),
                            tint = BlueText
                        )
                    },
                    supportingText = {
                        if (!emailCorrect) Text(
                            text = stringResource(R.string.formato_password_non_corretto),
                            color = Color.Red)
                    },
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

            var password by rememberSaveable { mutableStateOf("") }
            var passwordCorrect by remember { mutableStateOf(true) }
            var passwordPressed by remember { mutableStateOf(false) }
            Box(modifier = Modifier.padding(top = 15.dp)) {
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                            newText -> password = newText
                        // passwordCorrect = isValidPassword(password)
                    },
                    placeholder = { Text(text = stringResource(R.string.password)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 20.sp, color = BlueText),
                    trailingIcon = {
                        IconButton(
                            onClick = {passwordPressed = !passwordPressed}
                        ){
                            if(!passwordPressed){
                                Icon(
                                    painter = painterResource(id = R.drawable.eye_password_show_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp),
                                    tint = BlueText
                                )
                            }else{
                                Icon(
                                    painter = painterResource(id = R.drawable.eye_password_see_view_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp),
                                    tint = BlueText
                                )
                            }
                        }
                    },
                    supportingText = {
                        if (!passwordCorrect) Text(
                            text = stringResource(R.string.formato_password_non_corretto),
                            color = Color.Red)
                    },
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
                    ),
                    visualTransformation = if(!passwordPressed) PasswordVisualTransformation() else VisualTransformation.None
                )
            }

            val keyboardController = LocalSoftwareKeyboardController.current
            OutlinedButton(onClick = {
                emailCorrect = isValidEmail(email)
                passwordCorrect = isValidPassword(password)
                if (emailCorrect && passwordCorrect) {
                    keyboardController?.hide()
                    if(getCurrentConnectivityState(connectivityManager) == ConnectionState.Available){
                        viewModel.login(email, sha256(password))
                    }else {
                        showDialog = true
                    }
                }
            },
                modifier = Modifier
                    .padding(20.dp)
                    .widthIn(120.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, color = BlueText),
                content = {
                    Text(text = stringResource(R.string.login), modifier = Modifier
                        .align(Alignment.CenterVertically),
                        fontSize = 20.sp,
                        fontFamily = fonts,  color = WhiteText,
                        textAlign = TextAlign.Start)
                }
            )

            OutlinedButton(onClick = {navController.navigate(Screens.SIGN_IN_SCREEN)},
                modifier = Modifier
                    .widthIn(185.dp),
                content = {
                    Text(text = stringResource(R.string.non_sei_registrato), modifier = Modifier
                        .align(Alignment.CenterVertically),
                        fontSize = 15.sp,
                        fontFamily = fonts,
                        textAlign = TextAlign.Center,
                        color = WhiteText,
                        textDecoration = TextDecoration.Underline
                    )
                },
                border = BorderStroke(1.dp, Color.Transparent)
            )
        }
    }
    if(viewModel.loginDone){
        navController.navigate(Screens.HOME_SCREEN)
    }
}
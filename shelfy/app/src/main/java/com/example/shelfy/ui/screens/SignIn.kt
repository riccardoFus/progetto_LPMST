package com.example.shelfy.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shelfy.ui.BookHomePageViewModel
import com.example.shelfy.R
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlackPage
import com.example.shelfy.ui.theme.BlueText
import com.example.shelfy.ui.theme.WhiteText
import com.example.shelfy.ui.theme.fonts
import kotlinx.coroutines.delay

@Composable
fun SignIn(
    viewModel : BookHomePageViewModel,
    navController : NavHostController
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = BlackPage)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(color = BlackBar),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "SHELFY",
                color = BlueText,
                fontFamily = fonts,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
        Box(
            modifier = Modifier
                .weight(10f)
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally){

                Text(
                    text = "Sign In",
                    color = BlueText,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 11.dp),
                    fontSize = 40.sp,
                    fontFamily = fonts
                )
                var user by rememberSaveable { mutableStateOf("") }
                var userEmpty by remember { mutableStateOf(false) }
                Box() {
                    Row(modifier = Modifier) {
                        Icon(
                            painter = painterResource(id = R.drawable.profile_icon_512x512_w0uaq4yr),
                            contentDescription = "Profilo",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(50.dp)
                                .align(Alignment.CenterVertically),
                            tint = BlueText
                        )
                        TextField(
                            value = user,
                            onValueChange = { newText -> user = newText; if (user == "") userEmpty=true else false },
                            placeholder = { Text(text = "Username o Email", fontFamily = fonts)},
                            modifier = Modifier
                                .background(Color.White)
                                .border(width = 1.dp, color = if(userEmpty) Color.Red else Color.Black),
                            textStyle = TextStyle(fontSize = 20.sp, fontFamily = fonts)
                        )

                    }
                    OutlinedTextField(
                        value = user,
                        onValueChange = { newText -> user = newText },
                        placeholder = { Text(text = "Username")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        textStyle = TextStyle(fontSize = 20.sp, color = BlueText),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.profile_icon_512x512_w0uaq4yr),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp),
                                tint = BlueText
                            )
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
                    )
                }

                var email by rememberSaveable { mutableStateOf("") }
                Box(modifier = Modifier.padding(top = 20.dp)) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { newText -> email = newText },
                        placeholder = { Text(text = "Email")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
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
                }

                var password by rememberSaveable { mutableStateOf("") }
                var passwordEmpty by remember { mutableStateOf(false) }

                Box(modifier = Modifier.padding(top = 20.dp)) {
                    Row(){
                        Icon(
                            painter = painterResource(id = R.drawable.profile_icon_512x512_w0uaq4yr),
                            contentDescription = "Profilo",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(50.dp)
                                .align(Alignment.CenterVertically),
                            tint = BlueText
                        )
                        TextField(
                            value = password,
                            onValueChange = { newText -> password = newText; if (password == "") passwordEmpty = true else passwordEmpty = false},
                            placeholder = {Text(text = "Password", fontFamily = fonts)},
                            modifier = Modifier
                                .background(Color.White)
                                .border(width = 1.dp, color = if(passwordEmpty) Color.Red else Color.Black),
                            visualTransformation = PasswordVisualTransformation(),
                            textStyle = TextStyle(fontSize = 20.sp, fontFamily = fonts)
                        )
                    }
                }
                var password2 by rememberSaveable { mutableStateOf("") }
                var password2Empty by remember { mutableStateOf(false) }

                Box(modifier = Modifier.padding(top = 20.dp)) {
                    Row(){
                        Icon(
                            painter = painterResource(id = R.drawable.profile_icon_512x512_w0uaq4yr),
                            contentDescription = "Profilo",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(50.dp)
                                .align(Alignment.CenterVertically),
                            tint = BlueText
                        )
                        TextField(
                            value = password2,
                            onValueChange = { newText -> password2 = newText; if (password2 == "") password2Empty = true else password2Empty = false},
                            placeholder = {Text(text = "Conferma password", fontFamily = fonts)},
                            modifier = Modifier
                                .background(Color.White)
                                .border(width = 1.dp, color = if(password2Empty) Color.Red else Color.Black),
                            visualTransformation = PasswordVisualTransformation(),
                            textStyle = TextStyle(fontSize = 20.sp, fontFamily = fonts)
                        )
                    }
                }

                Button(onClick = {if (!passwordEmpty || !password2Empty || userEmpty){} }, modifier = Modifier
                    OutlinedTextField(
                        value = password,
                        onValueChange = { newText -> password = newText },
                        placeholder = { Text(text = "Password")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        textStyle = TextStyle(fontSize = 20.sp, color = BlueText),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.eye_password_show_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp),
                                tint = BlueText
                            )
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
                        visualTransformation = PasswordVisualTransformation()
                    )
                }
                var password2 by rememberSaveable { mutableStateOf("") }
                Box(modifier = Modifier.padding(top = 20.dp)) {
                    OutlinedTextField(
                        value = password2,
                        onValueChange = { newText -> password2 = newText },
                        placeholder = { Text(text = "Ripeti password")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        textStyle = TextStyle(fontSize = 20.sp, color = BlueText),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.eye_password_show_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp),
                                tint = BlueText
                            )
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
                        visualTransformation = PasswordVisualTransformation()
                    )
                }

                OutlinedButton(onClick = {}, modifier = Modifier
                    .padding(20.dp)
                    .widthIn(120.dp),
                    content = {
                        Text(text = "Sign In", modifier = Modifier
                            .align(Alignment.CenterVertically),
                            fontSize = 20.sp,
                            fontFamily = fonts,
                            textAlign = TextAlign.Center,
                            color = WhiteText)},
                    border = BorderStroke(1.dp, Color.Transparent),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = BlueText),
                )
                /* Button(onClick = {}, modifier = Modifier, colors = ButtonDefaults.buttonColors(containerColor = Color.White)){
=======
                /*
                Button(onClick = {}, modifier = Modifier, colors = ButtonDefaults.buttonColors(containerColor = Color.White)){
                    Icon(painter = painterResource(id = R.drawable.profile_icon_512x512_w0uaq4yr),
                        contentDescription = "Profilo",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(30.dp)
                            .align(Alignment.CenterVertically),
                        tint = BlackPage)
                    Text(text = "Sign In con Google", fontSize = 20.sp, fontFamily = fonts, color = Color.Black)

                } */

                Button(onClick = {navController.navigate("LOGIN_SCREEN")}, modifier = Modifier
                    .padding(top = 20.dp)
=======
                }
                */


                OutlinedButton(onClick = {navController.navigate("LOGIN_SCREEN")}, modifier = Modifier
                    .widthIn(185.dp),
                    content = {
                        Text(text = "Sei già un utente?", modifier = Modifier
                            .align(Alignment.CenterVertically),
                            fontSize = 19.sp,
                            fontFamily = fonts,
                            textAlign = TextAlign.Center,
                            color = BlueText)
                    },
                    border = BorderStroke(1.dp, BlueText)
                )
            }
        }

        Row(
            modifier = Modifier
                .background(color = BlackBar)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { navController.navigate("SEARCH_SCREEN") }) {
                Icon(
                    painter = painterResource(id = R.drawable.search_outline_1024x1024),
                    contentDescription = "Search",
                    tint = BlueText,
                    modifier = Modifier
                        .weight(1f)
                        .size(30.dp)
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.home_1024x919),
                    contentDescription = "Home",
                    tint = BlueText,
                    modifier = Modifier
                        .weight(1f)
                        .size(30.dp)
                )
            }
            IconButton(onClick = { navController.navigate("PROFILE_SCREEN") }) {
                Icon(
                    painter = painterResource(id = R.drawable.profile_icon_512x512_w0uaq4yr),
                    contentDescription = "Search",
                    tint = BlueText,
                    modifier = Modifier
                        .weight(1f)
                        .size(30.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun preview6(){
    SignIn(viewModel = BookHomePageViewModel(),
        navController = rememberNavController())
}


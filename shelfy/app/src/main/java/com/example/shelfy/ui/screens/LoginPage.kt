package com.example.shelfy.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.shelfy.MainActivity
import com.example.shelfy.R
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.navigation.Screens
import com.example.shelfy.ui.composables.ContentLoginPage
import com.example.shelfy.ui.theme.BlackBar
import com.example.shelfy.ui.theme.BlackPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginPage(
    viewModel: AppViewModel,
    navController: NavHostController,
    mainActivity: MainActivity
){
    if(!viewModel.openingDone) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(BlackBar, BlackPage)
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(250.dp))
            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier.size(250.dp)
            )
            GlobalScope.launch {
                Thread.sleep(500)
                if (FirebaseAuth.getInstance().currentUser != null) {
                    val dB: FirebaseFirestore = FirebaseFirestore.getInstance()
                    dB.collection("Users")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().currentUser!!.uid).get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                viewModel.username = document.get("username").toString()
                                viewModel.userId = document.id
                            }
                            viewModel.loginDone = true
                            navController.navigate(Screens.HOME_SCREEN)
                        }
                } else {
                    viewModel.openingDone = true
                }
            }
        }
    }else{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(BlackBar, BlackPage)
                    )
                )
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            ContentLoginPage(
                viewModel = viewModel,
                navController = navController,
                modifier = Modifier
                    .weight(10f)
                    .verticalScroll(rememberScrollState()),
                mainActivity = mainActivity)
        }
    }
}
package com.example.shelfy.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shelfy.MainActivity
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.screens.HomePage
import com.example.shelfy.ui.screens.ProfilePage
import com.example.shelfy.ui.screens.SearchPage
import com.example.shelfy.ui.screens.BookPage
import com.example.shelfy.ui.screens.LoginPage
import com.example.shelfy.ui.screens.SignInPage

@Composable
fun NavGraph(mainActivity: MainActivity, appViewModel: AppViewModel = viewModel()){
    // get the default nav controller (Manages app navigation within a NavHost,
    // handling navigation actions and back stack management)
    val navController = rememberNavController()
    // define the NavHost (A container that hosts a navigation graph,
    // defining the composable destinations and their navigation paths)
    NavHost(navController = navController, startDestination = Screens.LOGIN_SCREEN){
        composable(Screens.HOME_SCREEN){
            HomePage(viewModel = appViewModel, navController = navController, mainActivity = mainActivity)
        }
        composable(Screens.PROFILE_SCREEN){
            ProfilePage(viewModel = appViewModel, navController = navController, mainActivity = mainActivity)
        }
        composable(Screens.SEARCH_SCREEN){
            SearchPage(viewModel = appViewModel, navController = navController, mainActivity = mainActivity)
        }
        composable(Screens.VISUALIZER_SCREEN){
            BookPage(viewModel = appViewModel, navController = navController, mainActivity = mainActivity)
        }
        composable(Screens.LOGIN_SCREEN){
            LoginPage(viewModel = appViewModel, navController = navController, mainActivity = mainActivity)
        }
        composable(Screens.SIGN_IN_SCREEN){
            SignInPage(viewModel = appViewModel, navController = navController, mainActivity = mainActivity)
        }
    }
}
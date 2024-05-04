package com.example.shelfy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shelfy.ui.AppViewModel
import com.example.shelfy.ui.screens.HomePage
import com.example.shelfy.ui.screens.ProfilePage
import com.example.shelfy.ui.screens.SearchPage
import com.example.shelfy.ui.screens.BookPage
import com.example.shelfy.ui.screens.LoginPage
import com.example.shelfy.ui.screens.SignInPage

@Composable
fun NavGraph(appViewModel: AppViewModel = viewModel()){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.LOGIN_SCREEN){
        composable(Screens.HOME_SCREEN){
            HomePage(viewModel = appViewModel, navController = navController)
        }
        composable(Screens.PROFILE_SCREEN){
            ProfilePage(viewModel = appViewModel, navController = navController)
        }
        composable(Screens.SEARCH_SCREEN){
            SearchPage(viewModel = appViewModel, navController = navController)
        }
        composable(Screens.VISUALIZER_SCREEN){
            BookPage(viewModel = appViewModel, navController = navController)
        }
        composable(Screens.LOGIN_SCREEN){
            LoginPage(viewModel = appViewModel, navController = navController)
        }
        composable(Screens.SIGN_IN_SCREEN){
            SignInPage(viewModel = appViewModel, navController = navController)
        }

        /*
        composable("${Routes.WELCOME_SCREEN}/{${Routes.USER_NAME}}/{${Routes.ANIMAL_SELECTED}}",
            arguments = listOf(
                navArgument(name = Routes.USER_NAME){ type = NavType.StringType },
                navArgument(name = Routes.ANIMAL_SELECTED){ type = NavType.StringType }
            )
        ){
            WelcomeScreen()
        }
        */
    }
}

@Preview
@Composable
fun NavGraphPreview(){
    NavGraph()
}
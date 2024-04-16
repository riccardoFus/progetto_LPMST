package com.example.shelfy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shelfy.ui.BookHomePageViewModel
import com.example.shelfy.ui.screens.HomePage

@Composable
fun NavGraph(appViewModel: BookHomePageViewModel = viewModel()){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.HOME_SCREEN){
        composable(Screens.HOME_SCREEN){
            HomePage(viewModel = appViewModel, navController = navController)
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
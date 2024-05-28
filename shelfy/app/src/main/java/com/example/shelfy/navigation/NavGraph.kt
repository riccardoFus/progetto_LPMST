package com.example.shelfy.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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
        composable(Screens.HOME_SCREEN, enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                tween(5000)
            )
        }){
            HomePage(viewModel = appViewModel, navController = navController)
        }
        composable(Screens.PROFILE_SCREEN, enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                tween(5000)
            )
        }){
            ProfilePage(viewModel = appViewModel, navController = navController)
        }
        composable(Screens.SEARCH_SCREEN, enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                tween(5000)
            )
        }){
            SearchPage(viewModel = appViewModel, navController = navController)
        }
        composable(Screens.VISUALIZER_SCREEN, enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                tween(5000)
            )
        }){
            BookPage(viewModel = appViewModel, navController = navController)
        }
        composable(Screens.LOGIN_SCREEN, enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                tween(5000)
            )
        }){
            LoginPage(viewModel = appViewModel, navController = navController)
        }
        composable(Screens.SIGN_IN_SCREEN, enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                tween(5000)
            )
        }){
            SignInPage(viewModel = appViewModel, navController = navController)
        }
    }
}

@Preview
@Composable
fun NavGraphPreview(){
    NavGraph()
}
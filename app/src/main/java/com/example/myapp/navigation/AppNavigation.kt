package com.example.myapp.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.features.dollar.presentation.DollarScreen
import com.example.myapp.features.github.presentation.GithubScreen
import com.example.myapp.features.movie.presentation.PopularMoviesScreen
import com.example.myapp.features.profile.presentation.ProfileScreen

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Profile.route
    ) {
        composable(Screen.Github.route){
            GithubScreen(modifier = Modifier)
        }
        composable(Screen.Profile.route){
            ProfileScreen()
        }
       composable (Screen.Dollar.route ){
           DollarScreen()
       }

        composable (Screen.PopularMovies.route){
            PopularMoviesScreen()
        }
    }
}

package com.example.myapp.navigation

sealed class Screen (val route: String) {
    object Home: Screen("home")
    object Github: Screen ("github")
    object Profile: Screen ("profile")
    object Dollar: Screen ("dollar")
    object PopularMovies: Screen ("popularMovies")
}
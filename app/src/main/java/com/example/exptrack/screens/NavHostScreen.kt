package com.example.exptrack.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavHostScreen(modifier: Modifier){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "/home"){
        composable(route = "/home"){
            HomeScreen(modifier = modifier, navController)
        }
        composable(route = "/addScreen"){
            addScreen(navController)
        }
        composable( route = "/graphScreen"){
            GraphScreen(navController)
        }
    }
}
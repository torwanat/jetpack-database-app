package com.example.databaseapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.databaseapp.chat.Chat
import com.example.databaseapp.data.ProfileDao
import com.example.databaseapp.settings.SettingsScreen
import com.example.navigationapp.Routes


@Composable
fun AppNavigation(dao: ProfileDao){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.chat, builder = {
        composable(Routes.chat) {
            Chat(navController = navController, dao = dao)
        }
        composable(Routes.secondScreen) {
            SettingsScreen(navController = navController)
        }
    })
}
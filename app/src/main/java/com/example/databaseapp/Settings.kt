package com.example.databaseapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.databaseapp.ui.theme.DatabaseAppTheme
import com.example.navigationapp.Routes

@Composable
fun SettingsScreen(navController: NavController, dao: ProfileDao) {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    DatabaseAppTheme() {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(all = 10.dp)
            )
            TextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                    updateDatabaseSync(text.text, dao)
                }
            )
            Button(onClick = {
                navController.popBackStack(Routes.chat, false)
            }) {
                Text(text = "Go back to chat")
            }
        }
    }
}

fun updateDatabaseSync(username: String, dao: ProfileDao) {
    dao.upsert(Profile(username = username))
}
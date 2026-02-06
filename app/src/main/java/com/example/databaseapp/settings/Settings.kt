package com.example.databaseapp.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.databaseapp.AppViewModelProvider
import com.example.databaseapp.ui.theme.DatabaseAppTheme
import com.example.navigationapp.Routes

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val settingsUiState by viewModel.settingsUiState.collectAsState()

    SettingsScreenBody(
        navController = navController,
        settingsUiState = settingsUiState,
        onUsernameChange = { viewModel.updateUsername(it) }
    )
}

@Composable
fun SettingsScreenBody(
    navController: NavController,
    settingsUiState: SettingsViewModel.SettingsUiState,
    onUsernameChange: (String) -> Unit
){
    DatabaseAppTheme() {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PhotoContainer()
            Text(
                text = "Username",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(all = 10.dp)
            )
            TextField(
                value = settingsUiState.username,
                onValueChange = onUsernameChange
                )
            Button(onClick = {
                navController.popBackStack(Routes.chat, false)
            }) {
                Text(text = "Go back to chat")
            }
        }
    }
}

@Composable
fun PhotoContainer(){
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        photoUri = uri
    }

    Column (
        modifier = Modifier.padding(16.dp),
    ) {
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
            launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
        }) {
            Text(text = "Choose a photo")
        }

        if (photoUri != null) {
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = photoUri).build()
            )
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }

    }
}

package com.example.databaseapp.settings

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.databaseapp.AppViewModelProvider
import com.example.databaseapp.ui.theme.DatabaseAppTheme
import com.example.navigationapp.Routes
import java.io.File
import java.io.OutputStream

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
    val savedImageName = "profile_image.jpg"
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        uri?.let {
            context.savePhoto(it, savedImageName)
        }
    }

    Column (
        modifier = Modifier.padding(16.dp),
    ) {
        if (File(context.filesDir, savedImageName).exists()) {
            val savedPhotoUri = File(context.filesDir, savedImageName).toUri()
            Log.d("PhotoContainer", savedPhotoUri.toString())
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(savedPhotoUri)
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .padding(10.dp),
                contentScale = ContentScale.Crop
            )
        }
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
            launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
        }) {
            Text(text = "Choose a photo")
        }

    }
}

fun Context.savePhoto(photoUri: Uri, fileName: String) {
    val inputStream = contentResolver.openInputStream(photoUri)
    val outputStream: OutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
    inputStream?.use { input ->
        outputStream.use { output ->
            Log.d("PhotoContainer", "It works")
            input.copyTo(output)
        }
    }
    inputStream?.close()
    outputStream.close()
}

package com.example.databaseapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.databaseapp.ui.theme.DatabaseAppTheme
import com.example.navigationapp.Routes
import com.example.navigationapp.SampleData

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Row {
        Image(
            painter = painterResource(R.drawable.migotka),
            contentDescription = "Cat",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>, modifier: Modifier, navController: NavController) {
    Column(modifier) {
        Row(
            Modifier
                .padding(bottom = 10.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End,

        ) {
            IconButton(onClick = {
                navController.navigate(Routes.secondScreen)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_settings_24),
                    contentDescription = "Settings button"
                )
            }
        }
        LazyColumn {
            items(messages) {
                    message -> MessageCard(message)
            }
        }
    }
}

@Composable
fun Chat(navController: NavController, dao: ProfileDao) {
    DatabaseAppTheme {
        Scaffold(
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
        ) { padding ->
            Conversation(
                SampleData.conversationSample,
                modifier = Modifier.padding(padding),
                navController = navController
            )
        }
    }
}
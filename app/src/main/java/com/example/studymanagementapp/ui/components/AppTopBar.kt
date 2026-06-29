package com.example.studymanagementapp.ui.components

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/**
 * Eine wiederverwendbare, zentrale Top-Bar (Header), die auf verschiedenen Bildschirmen der Anwendung angezeigt wird.
 *
 * Die Bar passt ihren Titel dynamisch an den aktuellen Screen an und blendet im Querformat (Landscape) automatisch aus, um den Bildschirmplatz optimal zu nutzen.
 *
 * @param screen Der Name des aktuellen aktiven Bildschirms (z.B. "Fokus-Timer" oder "Lernplaner")
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTopBar(screen: String) {

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (!isLandscape) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(screen)
            }
        )
    }
}
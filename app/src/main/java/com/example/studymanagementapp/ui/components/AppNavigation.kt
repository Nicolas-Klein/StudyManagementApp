package com.example.studymanagementapp.ui.components

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.studymanagementapp.MainActivity
import com.example.studymanagementapp.PlanerActivity
import com.example.studymanagementapp.R
import com.example.studymanagementapp.data.NavigationTarget


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun SharedBottomNavigationBar(currentScreen: NavigationTarget) {
    val context = LocalContext.current

    NavigationBar {
        NavigationBarItem(
            selected = currentScreen == NavigationTarget.TIMER,
            label = { Text("Timer") },
            icon = { Icon(Icons.Filled.Timer, contentDescription = "Timer") },
            onClick = {
                if (currentScreen != NavigationTarget.TIMER) {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    context.startActivity(intent)

                    if (context is Activity) {
                        context.overrideActivityTransition(
                            Activity.OVERRIDE_TRANSITION_OPEN,
                            R.anim.fade_in,
                            R.anim.fade_out
                        )
                    }
                }
            }
        )

        NavigationBarItem(
            selected = currentScreen == NavigationTarget.PLANNER,
            label = { Text("Planer") },
            icon = { Icon(Icons.Filled.DateRange, contentDescription = "Planer") },
            onClick = {
                if (currentScreen != NavigationTarget.PLANNER) {
                    val intent = Intent(context, PlanerActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    context.startActivity(intent)

                    if (context is Activity) {
                        context.overrideActivityTransition(
                            Activity.OVERRIDE_TRANSITION_OPEN,
                            R.anim.fade_in,
                            R.anim.fade_out
                        )
                    }
                }
            }
        )
    }
}
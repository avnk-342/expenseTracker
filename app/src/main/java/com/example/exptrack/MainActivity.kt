package com.example.exptrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.exptrack.screens.NavHostScreen
import com.example.exptrack.ui.theme.ExpTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            ExpTrackTheme {
                Box(
                    contentAlignment = Alignment.Center
                ){
                    NavHostScreen(modifier = Modifier)
                }
            }
        }
    }
}

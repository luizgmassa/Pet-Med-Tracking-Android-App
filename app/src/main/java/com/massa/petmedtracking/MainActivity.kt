package com.massa.petmedtracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.massa.petmedtracking.presentation.ui.navigation.PetMedNavGraph
import com.massa.petmedtracking.ui.theme.PetMedTrackingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetMedTrackingTheme {
                PetMedNavGraph()
            }
        }
    }
}
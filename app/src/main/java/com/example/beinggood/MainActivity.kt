package com.example.beinggood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.beinggood.ui.screen.MainScreen
import com.example.beinggood.ui.theme.BeingGoodTheme
import com.example.beinggood.ui.screen.RoutineEditScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeingGoodTheme {
                MainScreen()
            }
        }
    }
}

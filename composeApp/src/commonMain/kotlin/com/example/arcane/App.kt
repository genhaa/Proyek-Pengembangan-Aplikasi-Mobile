package com.example.arcane

import androidx.compose.runtime.Composable
import com.example.arcane.presentation.navigation.AppNavHost
import com.example.arcane.presentation.theme.ArcaneTheme
import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        ArcaneTheme {
            AppNavHost()
        }
    }
}

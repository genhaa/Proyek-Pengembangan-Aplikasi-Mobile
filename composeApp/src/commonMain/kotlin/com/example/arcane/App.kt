package com.example.arcane

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.arcane.presentation.navigation.AppNavHost
import com.example.arcane.presentation.theme.ArcaneTheme
import com.example.arcane.presentation.screens.settings.SettingsViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    KoinContext {
        val settingsViewModel: SettingsViewModel = koinViewModel()

        val isDarkMode by settingsViewModel.isDarkMode.collectAsStateWithLifecycle()

        ArcaneTheme(darkTheme = isDarkMode) {
            AppNavHost()
        }
    }
}
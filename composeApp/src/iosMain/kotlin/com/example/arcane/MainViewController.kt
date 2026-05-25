package com.example.arcane

import androidx.compose.ui.window.ComposeUIViewController
import com.example.arcane.core.di.initKoinIOS

/**
 * iOS Main View Controller Factory
 * 
 * Digunakan oleh Swift/iOS untuk membuat UIViewController
 * yang menampilkan Compose UI.
 */
fun MainViewController() = ComposeUIViewController(
    configure = {
        // Initialize Koin for iOS
        initKoinIOS()
    }
) {
    App()
}

package com.example.arcane.core.network

import com.example.arcane.BuildConfig

/**
 * Android implementation of ApiConfig
 * 
 * Mengambil API key dari BuildConfig yang di-generate
 * dari local.properties saat build time.
 */
actual object ApiConfig {
    actual val geminiApiKey: String = BuildConfig.GEMINI_API_KEY
}

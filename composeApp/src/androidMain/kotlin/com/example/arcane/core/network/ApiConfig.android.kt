package com.example.arcane.core.network

import com.example.arcane.BuildConfig

actual object ApiConfig {
    actual val geminiApiKey: String = BuildConfig.GEMINI_API_KEY
    actual val googleBooksApiKey: String = BuildConfig.GOOGLE_BOOKS_API_KEY
}
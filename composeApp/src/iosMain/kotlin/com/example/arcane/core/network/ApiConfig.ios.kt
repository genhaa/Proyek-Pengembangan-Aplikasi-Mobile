package com.example.arcane.core.network

import platform.Foundation.NSBundle

/**
 * iOS implementation of ApiConfig
 * 
 * Mengambil API key dari Info.plist atau environment.
 */
actual object ApiConfig {
    actual val geminiApiKey: String
        get() {
            // Try to get from Info.plist
            val plistValue = NSBundle.mainBundle.objectForInfoDictionaryKey("GEMINI_API_KEY") as? String
            
            return plistValue ?: run {
                // Fallback untuk development
                ""
            }
        }
}

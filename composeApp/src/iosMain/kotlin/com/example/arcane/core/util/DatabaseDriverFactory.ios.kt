package com.example.arcane.core.util

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.arcane.data.local.ArcaneDatabase

/**
 * iOS implementation of DatabaseDriverFactory
 * 
 * Menggunakan NativeSqliteDriver yang membungkus SQLite native iOS.
 * Database disimpan di Documents directory aplikasi.
 */
actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = ArcaneDatabase.Schema,
            name = "arcane.db"
        )
    }
}

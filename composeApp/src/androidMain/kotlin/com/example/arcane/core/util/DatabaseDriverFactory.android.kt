package com.example.arcane.core.util

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.arcane.data.local.ArcaneDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = ArcaneDatabase.Schema,
            context = context,
            name = "arcane.db"
        )
    }
}

package com.example.arcane.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ==================== PALET WARNA ARCANE ====================
// Tema ungu/indigo gelap yang elegan sesuai identitas "Arcane" (perpustakaan digital)

private val ArcaneViolet = Color(0xFF7C3AED)          // Violet utama
private val ArcaneVioletLight = Color(0xFFA78BFA)     // Violet terang (dark mode)
private val ArcaneIndigo = Color(0xFF4338CA)           // Indigo sekunder
private val ArcaneIndigoContainer = Color(0xFFE0E7FF)  // Container indigo (light)
private val ArcanePurpleContainer = Color(0xFF4C1D95)  // Container violet (dark)

private val ArcaneGold = Color(0xFFF59E0B)            // Aksen emas untuk highlight
private val ArcaneGoldContainer = Color(0xFFFEF3C7)

private val LightBackground = Color(0xFFFAF9FF)
private val LightSurface = Color(0xFFFAF9FF)
private val LightSurfaceVariant = Color(0xFFEDE9FE)
private val LightOnBackground = Color(0xFF1E1635)
private val LightOnSurface = Color(0xFF1E1635)
private val LightOnSurfaceVariant = Color(0xFF5B4B8A)

private val DarkBackground = Color(0xFF0F0A1E)
private val DarkSurface = Color(0xFF1A1230)
private val DarkSurfaceVariant = Color(0xFF2D2050)
private val DarkOnBackground = Color(0xFFEDE9FE)
private val DarkOnSurface = Color(0xFFEDE9FE)
private val DarkOnSurfaceVariant = Color(0xFFC4B5FD)

private val ErrorColor = Color(0xFFDC2626)
private val ErrorContainerColor = Color(0xFFFEE2E2)
private val OnErrorContainerColor = Color(0xFF7F1D1D)

// ==================== SKEMA WARNA LIGHT ====================

private val LightColorScheme = lightColorScheme(
    primary = ArcaneViolet,
    onPrimary = Color.White,
    primaryContainer = ArcaneIndigoContainer,
    onPrimaryContainer = Color(0xFF2E1065),
    secondary = ArcaneIndigo,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFC7D2FE),
    onSecondaryContainer = Color(0xFF1E1B4B),
    tertiary = ArcaneGold,
    onTertiary = Color(0xFF451A03),
    tertiaryContainer = ArcaneGoldContainer,
    onTertiaryContainer = Color(0xFF451A03),
    error = ErrorColor,
    onError = Color.White,
    errorContainer = ErrorContainerColor,
    onErrorContainer = OnErrorContainerColor,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = Color(0xFF8B7CB6)
)

// ==================== SKEMA WARNA DARK ====================

private val DarkColorScheme = darkColorScheme(
    primary = ArcaneVioletLight,
    onPrimary = Color(0xFF2E1065),
    primaryContainer = ArcanePurpleContainer,
    onPrimaryContainer = ArcaneIndigoContainer,
    secondary = Color(0xFF818CF8),
    onSecondary = Color(0xFF1E1B4B),
    secondaryContainer = Color(0xFF312E81),
    onSecondaryContainer = Color(0xFFC7D2FE),
    tertiary = Color(0xFFFBBF24),
    onTertiary = Color(0xFF451A03),
    tertiaryContainer = Color(0xFF78350F),
    onTertiaryContainer = ArcaneGoldContainer,
    error = Color(0xFFFCA5A5),
    onError = Color(0xFF7F1D1D),
    errorContainer = Color(0xFF991B1B),
    onErrorContainer = Color(0xFFFEE2E2),
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = Color(0xFF7C5CBA)
)

// ==================== TEMA ARCANE ====================

@Composable
fun ArcaneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

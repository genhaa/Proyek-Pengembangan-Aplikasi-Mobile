package com.example.arcane.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Home : Route

    @Serializable
    data object Explore : Route

    @Serializable
    data class BookDetail(
        val googleBookId: String,
        val localBookId: Long = 0L
    ) : Route

    @Serializable
    data class ResearchAssistant(
        val bookTitle: String,
        val bookDescription: String
    ) : Route

    @Serializable
    data object Settings : Route
}

interface NavigationActions {
    fun navigateToHome()
    fun navigateToExplore()
    fun navigateToBookDetail(googleBookId: String, localBookId: Long = 0L)
    fun navigateToResearchAssistant(bookTitle: String, bookDescription: String)
    fun navigateToSettings()
    fun navigateBack()
}
package com.example.arcane.domain.repository

interface AIRepository {
    suspend fun summarizeLiterature(bookTitle: String, bookDescription: String): Result<String>
    suspend fun analyzeMethodology(bookTitle: String, bookDescription: String): Result<String>
    suspend fun connectConcepts(bookTitle: String, bookDescription: String): Result<String>
    suspend fun generateDiscussionQuestions(bookTitle: String, bookDescription: String): Result<String>
    suspend fun chat(bookTitle: String, bookDescription: String, question: String): Result<String>
    // Temporarily added to satisfy AIAssistantViewModel if needed, 
    // though the Impl uses the one with more params.
    suspend fun chat(prompt: String): Result<String> = Result.failure(Exception("Not implemented"))
}

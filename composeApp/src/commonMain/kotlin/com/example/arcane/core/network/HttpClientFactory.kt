package com.example.arcane.data.repository

import com.example.arcane.core.network.NetworkResult
import com.example.arcane.core.network.safeApiCall
import com.example.arcane.data.remote.api.ArcaneApiService
import com.example.arcane.domain.model.Book
import com.example.arcane.domain.repository.ArcaneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ArcaneRepositoryImpl(
    private val apiService: ArcaneApiService
) : ArcaneRepository {

    override fun searchBooks(query: String): Flow<NetworkResult<List<Book>>> = flow {
        emit(NetworkResult.Loading)

        val result = safeApiCall {
            val response = apiService.searchBooks(query)
            response.items?.map {
                Book(id = it.id, title = it.volumeInfo.title)
            } ?: emptyList()
        }

        emit(result)
    }

    override fun analyzeLiteratureWithAi(prompt: String): Flow<NetworkResult<String>> = flow {
        emit(NetworkResult.Loading)

        val result = safeApiCall {
            val response = apiService.askGemini(prompt)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "No response from Gemini"
        }

        emit(result)
    }
}
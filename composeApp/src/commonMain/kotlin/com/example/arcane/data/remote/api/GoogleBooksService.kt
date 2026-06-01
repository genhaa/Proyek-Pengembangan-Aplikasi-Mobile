package com.example.arcane.data.remote.api

import com.example.arcane.core.network.ApiConfig
import com.example.arcane.data.remote.dto.GoogleBooksResponse
import com.example.arcane.data.remote.dto.VolumeItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GoogleBooksService(private val client: HttpClient) {
    companion object {
        private const val BASE_URL = "https://www.googleapis.com/books/v1/volumes"
    }

    suspend fun searchBooks(query: String): GoogleBooksResponse = withContext(Dispatchers.IO) {
        client.get(BASE_URL) {
            parameter("q", query)
            parameter("maxResults", 20)
            parameter("key", ApiConfig.googleBooksApiKey)
        }.body()
    }

    // Tambahan baru — fetch detail buku by ID
    suspend fun getBookDetail(bookId: String): VolumeItem = withContext(Dispatchers.IO) {
        client.get("$BASE_URL/$bookId") {
            parameter("key", ApiConfig.googleBooksApiKey)
        }.body()
    }
}
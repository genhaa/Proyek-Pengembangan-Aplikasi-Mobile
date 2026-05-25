package com.example.arcane

import com.example.arcane.core.network.HttpClientFactory
import com.example.arcane.data.remote.api.GoogleBooksService
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class ApiLibraryTest {

    @Test
    fun testGoogleBooksApiReturnsData() = runTest {
        val client = HttpClientFactory.create(enableLogging = true)
        val service = GoogleBooksService(client)
        val response = service.searchBooks("Kotlin")
        assertTrue(response.items != null, "API harusnya mengembalikan daftar buku")
        println("Hasil API Pertama: ${response.items?.firstOrNull()?.volumeInfo?.title}")
    }
}
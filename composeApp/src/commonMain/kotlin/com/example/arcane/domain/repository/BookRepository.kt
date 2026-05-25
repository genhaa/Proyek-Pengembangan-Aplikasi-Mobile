package com.example.arcane.domain.repository

import com.example.arcane.domain.model.Book
import com.example.arcane.domain.model.ReadingStatus
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getAllBooks(): Flow<List<Book>>
    fun getBooksByStatus(status: ReadingStatus): Flow<List<Book>>
    fun getBookById(id: Long): Flow<Book?>
    suspend fun getBookByGoogleId(googleBookId: String): Book?
    suspend fun saveBook(book: Book): Long
    suspend fun deleteBook(id: Long)
    suspend fun updateBookStatus(id: Long, status: ReadingStatus)
    suspend fun updateBookNotesAndRating(id: Long, notes: String, rating: Int?)
    suspend fun searchBooks(query: String): List<Book>
}

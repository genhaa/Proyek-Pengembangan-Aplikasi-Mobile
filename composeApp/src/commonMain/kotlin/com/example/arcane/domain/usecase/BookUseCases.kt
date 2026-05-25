package com.example.arcane.domain.usecase

import com.example.arcane.domain.model.Book
import com.example.arcane.domain.model.ReadingStatus
import com.example.arcane.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

data class BookUseCases(
    val searchBooks: SearchBooksUseCase,
    val getBookshelf: GetBookshelfUseCase,
    val saveBook: SaveBookUseCase,
    val deleteBook: DeleteBookUseCase,
    val updateBookStatus: UpdateBookStatusUseCase,
    val updateBookNotes: UpdateBookNotesUseCase
)

class GetBookshelfUseCase(private val repository: BookRepository) {
    operator fun invoke(): Flow<List<Book>> = repository.getAllBooks()
    fun byStatus(status: ReadingStatus): Flow<List<Book>> = repository.getBooksByStatus(status)
}

class SearchBooksUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(query: String): Result<List<Book>> = runCatching {
        if (query.isBlank()) throw Exception("Masukkan kata kunci pencarian")
        repository.searchBooks(query)
    }
}

class SaveBookUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(book: Book): Result<Long> = runCatching {
        repository.saveBook(book)
    }
}

class DeleteBookUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(id: Long): Result<Unit> = runCatching {
        repository.deleteBook(id)
    }
}

class UpdateBookStatusUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(id: Long, status: ReadingStatus): Result<Unit> = runCatching {
        repository.updateBookStatus(id, status)
    }
}

class UpdateBookNotesUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(id: Long, notes: String, rating: Int?): Result<Unit> = runCatching {
        repository.updateBookNotesAndRating(id, notes, rating)
    }
}

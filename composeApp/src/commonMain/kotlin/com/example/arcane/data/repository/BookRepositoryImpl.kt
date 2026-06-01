package com.example.arcane.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.arcane.data.local.ArcaneDatabase
import com.example.arcane.data.local.entity.toDomain
import com.example.arcane.data.remote.api.GoogleBooksService
import com.example.arcane.domain.model.Book
import com.example.arcane.domain.model.ReadingStatus
import com.example.arcane.domain.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class BookRepositoryImpl(
    private val database: ArcaneDatabase,
    private val googleBooksService: GoogleBooksService
) : BookRepository {

    private val queries = database.bookQueries

    override fun getAllBooks(): Flow<List<Book>> =
        queries.getAllBooks()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { entities -> entities.map { it.toDomain() } }

    override fun getBooksByStatus(status: ReadingStatus): Flow<List<Book>> =
        queries.getBooksByStatus(status.name)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { entities -> entities.map { it.toDomain() } }

    override fun getBookById(id: Long): Flow<Book?> =
        queries.getBookById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .map { it?.toDomain() }

    override suspend fun getBookByGoogleId(googleBookId: String): Book? =
        withContext(Dispatchers.Default) {
            queries.getBookByGoogleId(googleBookId).executeAsOneOrNull()?.toDomain()
        }

    override suspend fun saveBook(book: Book): Long = withContext(Dispatchers.Default) {
        val now = Clock.System.now().toEpochMilliseconds()
        val existingBook = queries.getBookByGoogleId(book.googleBookId).executeAsOneOrNull()
        val createdAt = existingBook?.createdAt ?: now

        queries.insertBook(
            googleBookId = book.googleBookId,
            title = book.title,
            authors = book.authors.joinToString(", "),
            description = book.description.takeIf { it.isNotBlank() },
            coverUrl = book.coverUrl.takeIf { it.isNotBlank() },
            categories = book.categories.joinToString(", ").takeIf { it.isNotBlank() },
            publishedDate = book.publishedDate.takeIf { it.isNotBlank() },
            pageCount = book.pageCount?.toLong(),
            readingStatus = book.readingStatus.name,
            notes = book.notes,
            rating = book.rating?.toLong(),
            createdAt = createdAt,
            updatedAt = now
        )
        queries.getBookByGoogleId(book.googleBookId).executeAsOne().id
    }

    override suspend fun deleteBook(id: Long) = withContext(Dispatchers.Default) {
        queries.deleteBook(id)
    }

    override suspend fun updateBookStatus(id: Long, status: ReadingStatus) =
        withContext(Dispatchers.Default) {
            queries.updateBookStatus(
                readingStatus = status.name,
                updatedAt = Clock.System.now().toEpochMilliseconds(),
                id = id
            )
        }

    override suspend fun updateBookNotesAndRating(id: Long, notes: String, rating: Int?) =
        withContext(Dispatchers.Default) {
            queries.updateBookNotesAndRating(
                notes = notes,
                rating = rating?.toLong(),
                updatedAt = Clock.System.now().toEpochMilliseconds(),
                id = id
            )
        }

    override suspend fun searchBooks(query: String): List<Book> = withContext(Dispatchers.IO) {
        val response = googleBooksService.searchBooks(query)
        val domainBooks = response.items?.map { item ->
            val info = item.volumeInfo
            Book(
                googleBookId = item.id,
                title = info.title ?: "Unknown Title",
                authors = info.authors ?: emptyList(),
                description = info.description ?: "",
                coverUrl = (info.imageLinks?.thumbnail ?: info.imageLinks?.smallThumbnail ?: "")
                    .replace("http://", "https://"),
                categories = info.categories ?: emptyList(),
                publishedDate = info.publishedDate ?: "",
                pageCount = info.pageCount
            )
        } ?: emptyList()

        domainBooks.forEach { book ->
            val existingBook = queries.getBookByGoogleId(book.googleBookId).executeAsOneOrNull()
            if (existingBook == null) {
                val now = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
                queries.insertBook(
                    googleBookId = book.googleBookId,
                    title = book.title,
                    authors = book.authors.joinToString(", "),
                    description = book.description.takeIf { it.isNotBlank() },
                    coverUrl = book.coverUrl.takeIf { it.isNotBlank() },
                    categories = book.categories.joinToString(", ").takeIf { it.isNotBlank() },
                    publishedDate = book.publishedDate.takeIf { it.isNotBlank() },
                    pageCount = book.pageCount?.toLong(),
                    readingStatus = "Belum dibaca",
                    notes = "",
                    rating = 0L,
                    createdAt = now,
                    updatedAt = now
                )
            }
        }

        domainBooks
    }

    override suspend fun getBookDetail(googleBookId: String): Book? =
        withContext(Dispatchers.IO) {
            try {
                val item = googleBooksService.getBookDetail(googleBookId)
                val info = item.volumeInfo
                Book(
                    googleBookId = item.id,
                    title = info.title ?: "Unknown Title",
                    authors = info.authors ?: emptyList(),
                    description = info.description ?: "",
                    coverUrl = (info.imageLinks?.thumbnail ?: info.imageLinks?.smallThumbnail ?: "")
                        .replace("http://", "https://"),
                    categories = info.categories ?: emptyList(),
                    publishedDate = info.publishedDate ?: "",
                    pageCount = info.pageCount
                )
            } catch (e: Exception) {
                null
            }
        }
}
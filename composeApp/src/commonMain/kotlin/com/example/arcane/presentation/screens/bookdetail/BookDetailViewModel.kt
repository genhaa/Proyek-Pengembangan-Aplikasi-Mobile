package com.example.arcane.presentation.screens.bookdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcane.domain.model.Book
import com.example.arcane.domain.model.ReadingStatus
import com.example.arcane.domain.repository.BookRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookDetailUiState>(BookDetailUiState.Loading)
    val uiState: StateFlow<BookDetailUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<BookDetailEvent>()
    val events: SharedFlow<BookDetailEvent> = _events.asSharedFlow()

    fun loadBook(googleBookId: String, localId: Long, bookFromApi: Book? = null) {
        viewModelScope.launch {
            if (localId > 0) {
                repository.getBookById(localId).collect { book ->
                    _uiState.value = book?.let { BookDetailUiState.Success(it) } ?: BookDetailUiState.Error("Buku tidak ditemukan")
                }
            } else {
                val localBook = repository.getBookByGoogleId(googleBookId)
                if (localBook != null) {
                    _uiState.value = BookDetailUiState.Success(localBook)
                } else if (bookFromApi != null) {
                    _uiState.value = BookDetailUiState.Success(bookFromApi)
                } else {
                    try {
                        val apiResults = repository.searchBooks(googleBookId)
                        val matchedBook = apiResults.firstOrNull { it.googleBookId == googleBookId }

                        if (matchedBook != null) {
                            _uiState.value = BookDetailUiState.Success(matchedBook)
                        } else {
                            _uiState.value = BookDetailUiState.Error("Detail buku tidak ditemukan")
                        }
                    } catch (e: Exception) {
                        _uiState.value = BookDetailUiState.Error("Gagal memuat data dari internet")
                    }
                }
            }
        }
    }

    fun addToLibrary() {
        val currentState = _uiState.value
        if (currentState is BookDetailUiState.Success) {
            viewModelScope.launch {
                try {
                    val newId = repository.saveBook(currentState.book)

                    repository.getBookById(newId).collect { updatedBook ->
                        if (updatedBook != null) {
                            _uiState.value = BookDetailUiState.Success(updatedBook)
                            _events.emit(BookDetailEvent.ShowSnackbar("Buku berhasil ditambahkan ke perpustakaan!"))
                        }
                    }
                } catch (e: Exception) {
                    _events.emit(BookDetailEvent.ShowSnackbar("Gagal menyimpan ke perpustakaan"))
                }
            }
        }
    }

    fun updateStatus(status: ReadingStatus) {
        val currentState = _uiState.value
        if (currentState is BookDetailUiState.Success) {
            viewModelScope.launch {
                repository.updateBookStatus(currentState.book.id, status)
            }
        }
    }

    fun saveNotesAndRating(notes: String, rating: Int?) {
        val currentState = _uiState.value
        if (currentState is BookDetailUiState.Success) {
            viewModelScope.launch {
                repository.updateBookNotesAndRating(currentState.book.id, notes, rating)
                _events.emit(BookDetailEvent.ShowSnackbar("Catatan berhasil disimpan"))
            }
        }
    }

    fun deleteFromLibrary() {
        val currentState = _uiState.value
        if (currentState is BookDetailUiState.Success) {
            viewModelScope.launch {
                repository.deleteBook(currentState.book.id)
                _events.emit(BookDetailEvent.BookDeleted)
            }
        }
    }
}

sealed interface BookDetailUiState {
    data object Loading : BookDetailUiState
    data class Success(val book: Book) : BookDetailUiState
    data class Error(val message: String) : BookDetailUiState
}

sealed interface BookDetailEvent {
    data object BookDeleted : BookDetailEvent
    data class ShowSnackbar(val message: String) : BookDetailEvent
}
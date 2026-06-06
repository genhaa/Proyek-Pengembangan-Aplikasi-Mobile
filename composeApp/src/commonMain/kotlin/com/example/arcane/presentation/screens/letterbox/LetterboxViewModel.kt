package com.example.arcane.presentation.screens.letterbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcane.domain.model.Book
import com.example.arcane.domain.model.ReadingStatus
import com.example.arcane.domain.repository.AIRepository
import com.example.arcane.domain.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LetterboxViewModel(
    private val repository: BookRepository,
    private val aiRepository: AIRepository
) : ViewModel() {

    val uiState: StateFlow<LetterboxUiState> = repository
        .getBooksByStatus(ReadingStatus.COMPLETED)
        .map { books ->
            if (books.isEmpty()) LetterboxUiState.Empty
            else LetterboxUiState.Success(books)
        }
        .catch { e -> emit(LetterboxUiState.Error(e.message ?: "Terjadi kesalahan")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LetterboxUiState.Loading
        )

    // State untuk review per buku
    private val _reviewStates = MutableStateFlow<Map<String, ReviewState>>(emptyMap())
    val reviewStates: StateFlow<Map<String, ReviewState>> = _reviewStates.asStateFlow()

    // State untuk rekomendasi
    private val _recommendationState = MutableStateFlow<RecommendationState>(RecommendationState.Idle)
    val recommendationState: StateFlow<RecommendationState> = _recommendationState.asStateFlow()

    fun generateReview(book: Book) {
        viewModelScope.launch {
            _reviewStates.update { current ->
                current + (book.googleBookId to ReviewState.Loading)
            }
            val result = aiRepository.chat(
                bookTitle = book.title,
                bookDescription = book.description,
                question = "Buatkan ulasan singkat dan menarik untuk buku '${book.title}' karya ${book.authorsFormatted} dalam Bahasa Indonesia. Maksimal 3 paragraf."
            )
            result
                .onSuccess { review ->
                    _reviewStates.update { current ->
                        current + (book.googleBookId to ReviewState.Success(review))
                    }
                }
                .onFailure { error ->
                    _reviewStates.update { current ->
                        current + (book.googleBookId to ReviewState.Error(error.message ?: "Gagal generate review"))
                    }
                }
        }
    }

    fun getRecommendations(books: List<Book>) {
        viewModelScope.launch {
            _recommendationState.value = RecommendationState.Loading
            val bookTitles = books.joinToString(", ") { it.title }
            val result = aiRepository.chat(
                bookTitle = "Rekomendasi Buku",
                bookDescription = "Buku yang sudah dibaca: $bookTitles",
                question = "Berdasarkan buku-buku berikut yang sudah aku baca: $bookTitles. Rekomendasikan 5 buku lain yang mungkin aku sukai beserta alasan singkatnya dalam Bahasa Indonesia."
            )
            result
                .onSuccess { recommendation ->
                    _recommendationState.value = RecommendationState.Success(recommendation)
                }
                .onFailure { error ->
                    _recommendationState.value = RecommendationState.Error(error.message ?: "Gagal mendapat rekomendasi")
                }
        }
    }

    fun dismissRecommendation() {
        _recommendationState.value = RecommendationState.Idle
    }
}

// State untuk review per buku
sealed interface ReviewState {
    data object Idle : ReviewState
    data object Loading : ReviewState
    data class Success(val review: String) : ReviewState
    data class Error(val message: String) : ReviewState
}

// State untuk rekomendasi
sealed interface RecommendationState {
    data object Idle : RecommendationState
    data object Loading : RecommendationState
    data class Success(val recommendation: String) : RecommendationState
    data class Error(val message: String) : RecommendationState
}

sealed interface LetterboxUiState {
    data object Loading : LetterboxUiState
    data class Success(val books: List<Book>) : LetterboxUiState
    data object Empty : LetterboxUiState
    data class Error(val message: String) : LetterboxUiState
}
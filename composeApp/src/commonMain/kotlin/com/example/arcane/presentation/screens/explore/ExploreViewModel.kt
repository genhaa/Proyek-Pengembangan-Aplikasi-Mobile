package com.example.arcane.presentation.screens.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcane.domain.model.Book
import com.example.arcane.domain.repository.BookRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

val BOOK_GENRES = listOf(
    "Fiction", "Non-Fiction", "Science", "History",
    "Technology", "Philosophy", "Psychology", "Biography",
    "Fantasy", "Mystery", "Romance", "Self-Help"
)

@OptIn(FlowPreview::class)
class ExploreViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedGenre = MutableStateFlow<String?>(null)
    val selectedGenre: StateFlow<String?> = _selectedGenre.asStateFlow()

    private val _uiState = MutableStateFlow<ExploreUiState>(ExploreUiState.Initial)
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    init {
        _searchQuery
            .debounce(500)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isNotBlank()) {
                    searchBooks(query)
                } else if (_selectedGenre.value != null) {
                    searchBooks(_selectedGenre.value!!)
                } else {
                    _uiState.value = ExploreUiState.Initial
                }
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onGenreSelected(genre: String?) {
        _selectedGenre.value = genre
        if (genre != null) {
            searchBooks(genre)
        } else if (_searchQuery.value.isNotBlank()) {
            searchBooks(_searchQuery.value)
        } else {
            _uiState.value = ExploreUiState.Initial
        }
    }

    private fun searchBooks(query: String) {
        viewModelScope.launch {
            _uiState.value = ExploreUiState.Loading
            try {
                val results = repository.searchBooks(query)
                _uiState.value = if (results.isEmpty()) {
                    ExploreUiState.Empty
                } else {
                    ExploreUiState.Success(results)
                }
            } catch (e: Exception) {
                _uiState.value = ExploreUiState.Error(e.message ?: "Gagal memuat data")
            }
        }
    }
}

sealed interface ExploreUiState {
    data object Initial : ExploreUiState
    data object Loading : ExploreUiState
    data class Success(val books: List<Book>) : ExploreUiState
    data object Empty : ExploreUiState
    data class Error(val message: String) : ExploreUiState
}
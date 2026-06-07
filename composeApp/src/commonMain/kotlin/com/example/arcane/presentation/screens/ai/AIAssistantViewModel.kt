package com.example.arcane.presentation.screens.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcane.domain.repository.AIRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AIAssistantViewModel(
    private val aiRepository: AIRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AIAssistantUiState())
    val uiState: StateFlow<AIAssistantUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AIAssistantEvent>()
    val events: SharedFlow<AIAssistantEvent> = _events.asSharedFlow()

    fun setInitialText(text: String?) {
        if (text != null) {
            _uiState.update { it.copy(bookContext = text) }
        }
    }

    fun onChatInputChange(text: String) {
        _uiState.update { it.copy(chatInput = text, error = null) }
    }

    fun onActionSelected(action: AIAction) {
        _uiState.update { it.copy(selectedAction = action, result = null, error = null) }
    }

    fun executeAction() {
        val state = _uiState.value

        if (state.selectedAction == AIAction.CHAT && state.chatInput.isBlank()) {
            _uiState.update { it.copy(error = "Masukkan pertanyaanmu terlebih dahulu") }
            return
        }

        if (state.selectedAction != AIAction.CHAT && state.bookContext.isBlank()) {
            _uiState.update { it.copy(error = "Tidak ada konteks buku") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null, result = null) }

        viewModelScope.launch {
            val prompt = when (state.selectedAction) {
                AIAction.SUMMARIZE ->
                    "Berikan ringkasan singkat dan padat dalam Bahasa Indonesia dari buku berikut:\n\n${state.bookContext}"
                AIAction.RESEARCH_QUERY ->
                    "Analisis tema utama, pesan moral, dan pelajaran penting dari buku berikut dalam Bahasa Indonesia:\n\n${state.bookContext}"
                AIAction.CHAT ->
                    "Konteks buku:\n${state.bookContext}\n\nPertanyaan: ${state.chatInput}\n\nJawab dalam Bahasa Indonesia."
            }

            val result = aiRepository.chat(
                bookTitle = "Arcane Assistant",
                bookDescription = prompt,
                question = prompt
            )

            result
                .onSuccess { output ->
                    _uiState.update { it.copy(isLoading = false, result = output) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error.message ?: "Terjadi kesalahan")
                    }
                }
        }
    }

    fun copyResult() {
        val result = _uiState.value.result
        if (result != null) {
            viewModelScope.launch {
                _events.emit(AIAssistantEvent.CopyToClipboard(result))
            }
        }
    }

    fun applyToNote() {
        val result = _uiState.value.result
        if (result != null) {
            viewModelScope.launch {
                _events.emit(AIAssistantEvent.ApplyToNote(result))
            }
        }
    }
}

enum class AIAction(val displayName: String, val description: String) {
    SUMMARIZE("Ringkas", "Ringkas isi buku ini secara singkat"),
    RESEARCH_QUERY("Riset", "Analisis tema dan pesan utama buku"),
    CHAT("Tanya", "Tanya apapun tentang buku ini")
}

data class AIAssistantUiState(
    val bookContext: String = "",
    val chatInput: String = "",
    val selectedAction: AIAction = AIAction.SUMMARIZE,
    val isLoading: Boolean = false,
    val result: String? = null,
    val error: String? = null
) {
    val canExecute: Boolean
        get() = when (selectedAction) {
            AIAction.CHAT -> chatInput.isNotBlank() && !isLoading
            else -> bookContext.isNotBlank() && !isLoading
        }
}

sealed interface AIAssistantEvent {
    data class CopyToClipboard(val text: String) : AIAssistantEvent
    data class ApplyToNote(val text: String) : AIAssistantEvent
}
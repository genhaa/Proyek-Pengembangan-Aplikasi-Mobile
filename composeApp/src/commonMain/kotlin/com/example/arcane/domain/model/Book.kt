package com.example.arcane.domain.model

data class Book(
    val id: Long = 0,
    val googleBookId: String,
    val title: String,
    val authors: List<String> = emptyList(),
    val description: String = "",
    val coverUrl: String = "",
    val categories: List<String> = emptyList(),
    val publishedDate: String = "",
    val pageCount: Int? = null,
    val readingStatus: ReadingStatus = ReadingStatus.TO_READ,
    val notes: String = "",
    val rating: Int? = null,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
) {
    val authorsFormatted: String
        get() = authors.joinToString(", ").ifBlank { "Penulis tidak diketahui" }

    val categoriesFormatted: String
        get() = categories.joinToString(", ").ifBlank { "Umum" }

    val descriptionPreview: String
        get() = if (description.length > 200) description.take(200) + "..." else description

    val isInLibrary: Boolean
        get() = id > 0
}

enum class ReadingStatus(val displayName: String) {
    TO_READ("Belum Dibaca"),
    READING("Sedang Dibaca"),
    COMPLETED("Selesai");

    companion object {
        fun fromString(value: String): ReadingStatus {
            return entries.find { it.name == value } ?: TO_READ
        }
    }
}

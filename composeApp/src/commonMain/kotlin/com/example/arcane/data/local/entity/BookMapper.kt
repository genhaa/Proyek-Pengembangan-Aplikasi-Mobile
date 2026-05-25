package com.example.arcane.data.local.entity

import com.example.arcane.data.local.BookEntity
import com.example.arcane.domain.model.Book
import com.example.arcane.domain.model.ReadingStatus

fun BookEntity.toDomain(): Book {
    return Book(
        id = id,
        googleBookId = googleBookId,
        title = title,
        authors = if (authors.isEmpty()) emptyList() else authors.split(", "),
        description = description ?: "",
        coverUrl = coverUrl ?: "",
        categories = if (categories.isNullOrEmpty()) emptyList() else categories.split(", "),
        publishedDate = publishedDate ?: "",
        pageCount = pageCount?.toInt(),
        readingStatus = try {
            ReadingStatus.valueOf(readingStatus)
        } catch (e: Exception) {
            ReadingStatus.TO_READ
        },
        notes = notes,
        rating = rating?.toInt(),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Book.toEntityValues(): BookEntity {
    return BookEntity(
        id = id,
        googleBookId = googleBookId,
        title = title,
        authors = authors.joinToString(", "),
        description = description,
        coverUrl = coverUrl,
        categories = categories.joinToString(", "),
        publishedDate = publishedDate,
        pageCount = pageCount?.toLong(),
        readingStatus = readingStatus.name,
        notes = notes,
        rating = rating?.toLong(),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

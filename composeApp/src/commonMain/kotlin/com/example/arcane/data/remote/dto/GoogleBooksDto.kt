package com.example.arcane.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GoogleBooksResponse(
    val items: List<GoogleBookItem>? = null
)

@Serializable
data class GoogleBookItem(
    val id: String,
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String,
    val authors: List<String>? = null,
    val description: String? = null,
    val imageLinks: ImageLinks? = null,
    val categories: List<String>? = null,
    val publishedDate: String? = null,
    val pageCount: Int? = null
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null
)

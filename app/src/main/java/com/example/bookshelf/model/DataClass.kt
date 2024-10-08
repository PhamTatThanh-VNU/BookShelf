package com.example.bookshelf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleBooksResponse(
    @SerialName("items") val items: List<Book>? = null
)
@Serializable
data class Book(
    @SerialName("id") val id: String ="Not found",
    @SerialName("volumeInfo") val volumeInfo: VolumeInfo = VolumeInfo()
)
@Serializable
data class VolumeInfo(
    @SerialName("title") val title: String = "Not found",
    @SerialName("authors") val authors: List<String>? = null,
    @SerialName("imageLinks") val imageLinks: ImageLinks? = null,
    @SerialName("description") val description: String = "Not found"
)
@Serializable
data class ImageLinks(
    @SerialName("thumbnail") val thumbnail: String = ""
)
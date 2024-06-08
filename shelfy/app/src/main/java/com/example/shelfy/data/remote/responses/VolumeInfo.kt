package com.example.shelfy.data.remote.responses

data class VolumeInfo(
    val allowAnonLogging: Boolean = false,
    val authors: List<String> = emptyList(),
    val averageRating: Float = 0f,
    val canonicalVolumeLink: String = "",
    val categories: List<String> = emptyList(),
    val contentVersion: String = "",
    val description: String = "",
    val imageLinks: ImageLinks = ImageLinks(),
    val industryIdentifiers: List<IndustryIdentifier> = emptyList(),
    val infoLink: String = "",
    val language: String = "",
    val maturityRating: String = "",
    val pageCount: Long = 0,
    val panelizationSummary: PanelizationSummary = PanelizationSummary(),
    val previewLink: String = "",
    val printType: String = "",
    val publishedDate: String = "",
    val publisher: String = "",
    val ratingsCount: Long = 0,
    val readingModes: ReadingModes = ReadingModes(),
    val subtitle: String = "",
    val title: String = ""
)
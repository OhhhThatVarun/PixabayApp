package com.varun.pixabayapp.domain.entities


class SearchResponse(
    val nextPage: Int?,
    val previousPage: Int?,
    val images: List<Image>
)
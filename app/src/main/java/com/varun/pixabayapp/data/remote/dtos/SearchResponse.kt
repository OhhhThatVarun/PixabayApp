package com.varun.pixabayapp.data.remote.dtos


import com.google.gson.annotations.SerializedName
import com.varun.pixabayapp.domain.entities.SearchResponse


class SearchResponse(

    @SerializedName("totalHits")
    val totalHits: Int,

    @SerializedName("hits")
    val hits: List<Image>
) {

    fun toDomainEntity(nextPage: Int?, previousPage: Int?): SearchResponse {
        return SearchResponse(nextPage, previousPage, hits.map { it.toDomainEntity() })
    }
}
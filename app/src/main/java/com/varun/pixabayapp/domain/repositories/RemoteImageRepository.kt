package com.varun.pixabayapp.domain.repositories

import com.varun.pixabayapp.domain.entities.SearchResponse
import com.varun.pixabayapp.domain.utils.Resource


interface RemoteImageRepository {
    suspend fun search(query: String, currentPage: Int): Resource<SearchResponse>
}
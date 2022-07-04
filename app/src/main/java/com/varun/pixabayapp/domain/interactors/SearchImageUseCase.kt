package com.varun.pixabayapp.domain.interactors

import com.varun.pixabayapp.domain.entities.SearchResponse
import com.varun.pixabayapp.domain.repositories.LocalImageRepository
import com.varun.pixabayapp.domain.repositories.RemoteImageRepository
import com.varun.pixabayapp.domain.utils.Resource
import javax.inject.Inject


class SearchImageUseCase @Inject constructor(private val remoteImageRepository: RemoteImageRepository) {

    suspend fun execute(query: String, page: Int): Resource<SearchResponse> {
        return remoteImageRepository.search(query, page)
    }
}
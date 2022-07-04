package com.varun.pixabayapp.domain.repositories

import com.varun.pixabayapp.domain.entities.Image
import com.varun.pixabayapp.domain.entities.SearchResponse
import com.varun.pixabayapp.domain.utils.Resource


interface LocalImageRepository {
    suspend fun search(query: String): Resource<SearchResponse>
    suspend fun saveImages(images: List<Image>): Resource<Unit>
    //suspend fun getImagesPagingSource(): PagingSource<Int, Image>
}
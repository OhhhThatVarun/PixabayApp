package com.varun.pixabayapp.data.remote.repositories

import com.varun.pixabayapp.data.remote.apis.PixabayImageApi
import com.varun.pixabayapp.data.utils.safeApiCallWithCoroutine
import com.varun.pixabayapp.domain.config.SearchImageConfig.MAX_IMAGES_PER_PAGE
import com.varun.pixabayapp.domain.config.SearchImageConfig.STARTING_PAGE_INDEX
import com.varun.pixabayapp.domain.repositories.RemoteImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.net.URLEncoder
import javax.inject.Inject


class RemoteImageRepositoryImpl @Inject constructor(private val api: PixabayImageApi, private val apiKey: String, private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) : RemoteImageRepository {

    override suspend fun search(query: String, currentPage: Int) = safeApiCallWithCoroutine(coroutineDispatcher) {
        val urlEncodedQuery = encodeQuery(query)
        val response = api.search(urlEncodedQuery, currentPage, MAX_IMAGES_PER_PAGE, apiKey)
        val totalPages = (response.totalHits + MAX_IMAGES_PER_PAGE - 1) / MAX_IMAGES_PER_PAGE
        val nextPage = if (currentPage >= totalPages) null else currentPage + 1
        val previousPage = if (currentPage <= STARTING_PAGE_INDEX) null else currentPage - 1
        response.toDomainEntity(nextPage, previousPage)
    }

    private fun encodeQuery(query: String): String {
        return URLEncoder.encode(query, "UTF-8")
    }
}
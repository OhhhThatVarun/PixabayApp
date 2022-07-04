package com.varun.pixabayapp.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.varun.pixabayapp.domain.config.SearchImageConfig
import com.varun.pixabayapp.domain.entities.Image
import com.varun.pixabayapp.domain.interactors.SearchImageUseCase
import com.varun.pixabayapp.domain.utils.Resource


class ImageSearchPagingSource(private val query: String, private val searchImageUseCase: SearchImageUseCase) : PagingSource<Int, Image>() {

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return when (val resource = searchImageUseCase.execute(query, params.key ?: SearchImageConfig.STARTING_PAGE_INDEX)) {
            is Resource.Success -> {
                val result = resource.data
                LoadResult.Page(result.images, result.previousPage, result.nextPage)
            }
            is Resource.Failure -> {
                LoadResult.Error(resource.throwable)
            }
        }
    }
}
package com.varun.pixabayapp.presentation.features.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.varun.pixabayapp.domain.config.SearchImageConfig.MAX_IMAGES_IN_MEMORY
import com.varun.pixabayapp.domain.config.SearchImageConfig.MAX_IMAGES_PER_PAGE
import com.varun.pixabayapp.domain.interactors.SearchImageUseCase
import com.varun.pixabayapp.presentation.extensions.debounce
import com.varun.pixabayapp.presentation.paging.ImageSearchPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(private val searchImageUseCase: SearchImageUseCase) : ViewModel() {

    val searchQuery = MutableLiveData("fruits")

    val images = searchQuery.debounce(coroutineScope = viewModelScope).switchMap {
        if (getCurrentValidSearchQuery().isNotBlank()) {
            imagePager.liveData.cachedIn(viewModelScope) //.map { it.map { image -> image.toDomainEntity() } }
        } else {
            MutableLiveData()
        }
    }

//    @OptIn(ExperimentalPagingApi::class)
//    private val imagePager by lazy {
//        val searchPagingConfig = PagingConfig(pageSize = IMAGES_PER_PAGE, maxSize = MAX_IMAGES_IN_MEMORY, enablePlaceholders = false)
//        val remoteMediator = PixabayRemoteMediator(getCurrentValidSearchQuery(), database, searchImageUseCase)
//        Pager(config = searchPagingConfig, remoteMediator = remoteMediator) {
//            imageDao.getPagingSource()
//        }
//    }

    private val imagePager by lazy {
        val searchPagingConfig = PagingConfig(pageSize = MAX_IMAGES_PER_PAGE, maxSize = MAX_IMAGES_IN_MEMORY, enablePlaceholders = false)
        Pager(config = searchPagingConfig) {
            ImageSearchPagingSource(getCurrentValidSearchQuery(), searchImageUseCase)
        }
    }

    fun retrySearch() {
        searchQuery.postValue(getCurrentValidSearchQuery())
    }

    private fun getCurrentValidSearchQuery(): String {
        return searchQuery.value?.trim() ?: String()
    }
}
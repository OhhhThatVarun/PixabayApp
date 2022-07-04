package com.varun.pixabayapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.varun.pixabayapp.data.local.PixabayAppDatabase
import com.varun.pixabayapp.data.local.entities.Image
import com.varun.pixabayapp.data.local.entities.RemoteKeys
import com.varun.pixabayapp.data.local.entities.toDbEntity
import com.varun.pixabayapp.domain.config.SearchImageConfig
import com.varun.pixabayapp.domain.interactors.SearchImageUseCase
import com.varun.pixabayapp.domain.utils.Resource


@OptIn(ExperimentalPagingApi::class)
class PixabayRemoteMediator(private val query: String, private val database: PixabayAppDatabase, private val searchImageUseCase: SearchImageUseCase) : RemoteMediator<Int, Image>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Image>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: SearchImageConfig.STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        return when (val resource = searchImageUseCase.execute(query, page)) {
            is Resource.Success -> {
                val prevKey = resource.data.previousPage
                val nextKey = resource.data.nextPage
                val images = resource.data.images.map { it.toDbEntity() }
                val keys = images.map { RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey) }
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.getImageDao().deleteAll()
                        database.getRemoteKeyDao().clearRemoteKeys()
                    }
                    database.getRemoteKeyDao().insertAll(keys)
                    database.getImageDao().insertAll(images)
                }
                MediatorResult.Success(endOfPaginationReached = nextKey == null)
            }
            is Resource.Failure -> {
                MediatorResult.Error(resource.throwable)
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Image>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.getRemoteKeyDao().getRemoteKeysImageId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Image>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { image ->
            database.getRemoteKeyDao().getRemoteKeysImageId(image.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Image>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { image ->
            database.getRemoteKeyDao().getRemoteKeysImageId(image.id)
        }
    }
}
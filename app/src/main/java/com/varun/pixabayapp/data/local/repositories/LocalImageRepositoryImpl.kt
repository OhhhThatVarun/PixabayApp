package com.varun.pixabayapp.data.local.repositories

import com.varun.pixabayapp.data.local.daos.ImageDao
import com.varun.pixabayapp.data.local.entities.toDbEntity
import com.varun.pixabayapp.data.utils.safeApiCallWithCoroutine
import com.varun.pixabayapp.domain.entities.Image
import com.varun.pixabayapp.domain.repositories.LocalImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class LocalImageRepositoryImpl @Inject constructor(private val dao: ImageDao, private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) : LocalImageRepository {

    override suspend fun search(query: String) = safeApiCallWithCoroutine(coroutineDispatcher) {
        TODO()
    }

    override suspend fun saveImages(images: List<Image>) = safeApiCallWithCoroutine(coroutineDispatcher) {
        dao.insertAll(images.map { it.toDbEntity() })
    }
}
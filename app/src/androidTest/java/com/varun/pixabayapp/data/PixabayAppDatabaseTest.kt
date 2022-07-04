package com.varun.pixabayapp.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.varun.pixabayapp.data.local.PixabayAppDatabase
import com.varun.pixabayapp.data.local.daos.ImageDao
import com.varun.pixabayapp.data.local.entities.Image
import com.varun.pixabayapp.data.local.entities.toDbEntity
import com.varun.pixabayapp.data.remote.dtos.SearchResponse
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStream


@RunWith(AndroidJUnit4::class)
class PixabayAppDatabaseTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private lateinit var db: PixabayAppDatabase
    private lateinit var imageDao: ImageDao

    private val images: List<com.varun.pixabayapp.data.remote.dtos.Image> by lazy {
        val jsonStream: InputStream = context.resources.assets.open("response.json")
        val jsonBytes: ByteArray = jsonStream.readBytes()
        Gson().fromJson(String(jsonBytes), SearchResponse::class.java).hits
    }

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(context, PixabayAppDatabase::class.java).build()
        imageDao = db.getImageDao()
    }

    @After
    fun clear() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun shouldInsertImageIntoTheDB() = runBlocking {
        val testImage = images[0].toDomainEntity().toDbEntity()

        imageDao.insert(testImage)

        val testRetrievedImage = imageDao.getImage(testImage.id)
        assertEquals(testRetrievedImage?.id, testImage.id)
    }

    @Test
    fun shouldDeleteAllImagesFromTheDB() = runBlocking {
        val testImages = images.map { it.toDomainEntity() }.map { it.toDbEntity() }

        imageDao.insertAll(testImages)
        imageDao.deleteAll()

        val testRetrievedImages = mutableListOf<Image?>()
        testImages.forEach {
            imageDao.getImage(it.id)?.let { image ->
                testRetrievedImages.add(image)
            }
        }
        assertEquals(0, testRetrievedImages.size)
    }
}
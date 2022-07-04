package com.varun.pixabayapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.varun.pixabayapp.data.remote.apis.PixabayImageApi
import com.varun.pixabayapp.data.remote.dtos.SearchResponse
import com.varun.pixabayapp.data.remote.repositories.RemoteImageRepositoryImpl
import com.varun.pixabayapp.domain.config.SearchImageConfig
import com.varun.pixabayapp.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.testng.Assert.assertEquals
import org.testng.annotations.AfterTest
import java.io.FileInputStream
import java.io.InputStream


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class RemoteImageRepositoryImplTest {

    @get:Rule
    val instantTaskExecutionRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val pixabayImageApi = mock(PixabayImageApi::class.java)

    private val searchResponse: SearchResponse by lazy {
        val jsonStream: InputStream = FileInputStream("src/main/assets/response.json")
        val jsonBytes: ByteArray = jsonStream.readBytes()
        Gson().fromJson(String(jsonBytes), SearchResponse::class.java)
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should set correct page number when mapping to domain entity`() = runTest {
        val apiKey = "1234"
        val query = "fruits"
        pixabayImageApi.apply {
            `when`(search(query, null, SearchImageConfig.MAX_IMAGES_PER_PAGE, apiKey)).thenReturn(searchResponse)
        }
        val repo = RemoteImageRepositoryImpl(pixabayImageApi, apiKey)

        val resource = repo.search(query, SearchImageConfig.STARTING_PAGE_INDEX)

        when (resource) {
            is Resource.Success -> {
                assertEquals(resource.data.nextPage, 1)
                assertEquals(resource.data.previousPage, null)
                assertEquals(resource.data.images.size, 20)
            }
            is Resource.Failure -> {

            }
        }
    }
}
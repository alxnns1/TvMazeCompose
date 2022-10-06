package com.alxnns1.tvmazecompose.network

import com.alxnns1.tvmazecompose.model.ImageResponse
import com.alxnns1.tvmazecompose.model.ShowDetails
import com.alxnns1.tvmazecompose.model.ShowDetailsResponse
import com.alxnns1.tvmazecompose.utils.DateUtils
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class TvMazeRepositoryTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `singleSearch with success returns show details`() = runTest {
        val showDetailsResponse = Response.success(
            ShowDetailsResponse(
                "name",
                ImageResponse("mediumImage", "originalImage"),
                "premiered"
            )
        )
        val tvMazeService = mockk<TvMazeService> {
            coEvery { singleSearch("test") } returns showDetailsResponse
        }
        val dateUtils = mockk<DateUtils> {
            every { daysSinceDateString("premiered") } returns 5L
        }

        val subject = TvMazeRepository(tvMazeService, dateUtils)

        val actual = subject.singleSearch("test")
        advanceUntilIdle()

        val expected = ResultOf.Success(ShowDetails("name", "originalImage", 5L))

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `singleSearch with error returns error`() = runTest {
        val showDetailsResponse = Response.error<ShowDetailsResponse>(400, mockk(relaxed = true))
        val tvMazeService = mockk<TvMazeService> {
            coEvery { singleSearch("test") } returns showDetailsResponse
        }
        val dateUtils = mockk<DateUtils>()

        val subject = TvMazeRepository(tvMazeService, dateUtils)

        val actual = subject.singleSearch("test")
        advanceUntilIdle()

        val expected = ResultOf.Error

        Assert.assertEquals(expected, actual)
    }
}
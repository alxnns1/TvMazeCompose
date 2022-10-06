package com.alxnns1.tvmazecompose

import com.alxnns1.tvmazecompose.model.ShowDetails
import com.alxnns1.tvmazecompose.network.ResultOf
import com.alxnns1.tvmazecompose.network.TvMazeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TvMazeViewModelTest {

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
    fun `singleSearch calls API with search query and emits result`() = runTest {
        val showDetailsResult = ResultOf.Success(ShowDetails("name", "image", 0L))
        val tvMazeRepository = mockk<TvMazeRepository> {
            coEvery { singleSearch("test") } returns showDetailsResult
        }

        val subject = TvMazeViewModel(tvMazeRepository)

        subject.singleSearch("test")
        advanceUntilIdle()

        coVerify { tvMazeRepository.singleSearch("test") }
        assertEquals(showDetailsResult, subject.showDetailsState.value)
    }
}
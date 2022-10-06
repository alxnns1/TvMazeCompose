package com.alxnns1.tvmazecompose.network

import com.alxnns1.tvmazecompose.model.ShowDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TvMazeService {

    @GET("singlesearch/shows")
    suspend fun singleSearch(@Query("q") query: String): Response<ShowDetailsResponse>
}
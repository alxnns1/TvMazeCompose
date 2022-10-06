package com.alxnns1.tvmazecompose.network

import com.alxnns1.tvmazecompose.model.ShowDetails
import com.alxnns1.tvmazecompose.utils.DateUtils
import javax.inject.Inject

class TvMazeRepository @Inject constructor(
    private val tvMazeService: TvMazeService,
    private val dateUtils: DateUtils
) {

    suspend fun singleSearch(query: String): ResultOf<ShowDetails> {
        try {
            val searchResponse = tvMazeService.singleSearch(query)
            return if (searchResponse.isSuccessful && searchResponse.body() != null) {
                val body = searchResponse.body()!!
                ResultOf.Success(
                    ShowDetails(
                        body.name,
                        body.image.original,
                        dateUtils.daysSinceDateString(body.premiered)
                    )
                )
            } else {
                ResultOf.Error
            }
        } catch (e: Exception) {
            return ResultOf.Error
        }
    }
}
package com.alxnns1.tvmazecompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alxnns1.tvmazecompose.model.ShowDetails
import com.alxnns1.tvmazecompose.network.ResultOf
import com.alxnns1.tvmazecompose.network.TvMazeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvMazeViewModel @Inject constructor(
    private val tvMazeRepository: TvMazeRepository
) : ViewModel() {

    private val _showDetailsState = MutableStateFlow<ResultOf<ShowDetails>>(ResultOf.Initial)
    val showDetailsState: StateFlow<ResultOf<ShowDetails>> = _showDetailsState.asStateFlow()

    fun singleSearch(query: String) {
        viewModelScope.launch {
            _showDetailsState.emit(tvMazeRepository.singleSearch(query))
        }
    }
}
package com.acet.moviescinemawithkmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acet.shared_mobile.data.model.AppError
import com.acet.shared_mobile.data.model.ResultWrapper
import com.acet.shared_mobile.data.model.dto.MovieDTO
import com.acet.shared_mobile.domain.usecase.GetMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getMovieUseCase: GetMovieUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            setLoading(true)
            val result = getMovieUseCase.invoke()

            when (result) {
                is ResultWrapper.Success -> {
                    _uiState.update {
                        it.copy(
                            movies = result.data ?: emptyList(),
                        )
                    }
                }

                is ResultWrapper.Error -> {
                    _uiState.update {
                        it.copy(
                            appError = result.error
                        )
                    }
                }
            }

            setLoading(false)
        }
    }

    fun onPullRefresh() {
        getMovies()
    }

    private fun setLoading(isLoading: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }
}

data class MainUiState(
    val isLoading: Boolean = false,
    val movies: List<MovieDTO> = emptyList(),
    val appError: AppError? = null
)

data class MainUiEvent(
    val onPullRefresh: () -> Unit = {}
)
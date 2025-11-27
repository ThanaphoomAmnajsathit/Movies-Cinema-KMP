package com.acet.shared_mobile.domain.usecase

import com.acet.shared_mobile.data.mapper.AppErrorMapper
import com.acet.shared_mobile.data.model.ResultWrapper
import com.acet.shared_mobile.data.model.dto.MovieDTO
import com.acet.shared_mobile.data.remote.MovieService

class GetMovieUseCase(
    private val movieService: MovieService
) {
    suspend operator fun invoke(): ResultWrapper<List<MovieDTO>?> {
        return try {
            movieService.getMovies()
        } catch (ex: Exception) {
            ResultWrapper.Error(error = AppErrorMapper.map(ex))
        }
    }
}
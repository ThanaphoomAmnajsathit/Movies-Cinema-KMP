package com.acet.shared_mobile.data.remote

import com.acet.shared_mobile.data.mapper.AppErrorMapper
import com.acet.shared_mobile.data.model.ApiResponse
import com.acet.shared_mobile.data.model.ResultWrapper
import com.acet.shared_mobile.data.model.dto.MovieDTO
import com.acet.shared_mobile.data.model.response.MovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MovieServiceImpl(
    private val client: HttpClient
): MovieService {
    override suspend fun getMovies(): ResultWrapper<List<MovieDTO>?> {
        try {
            val httpResponse = client.get("/3/trending/movie/day?language=en-US")
            val response = httpResponse.body<ApiResponse<List<MovieResponse>>>()

            return ResultWrapper.Success(data = response.results?.map { it.toDTO() })
        } catch (ex: Exception) {
            return ResultWrapper.Error(error = AppErrorMapper.map(ex))
        }
    }
}
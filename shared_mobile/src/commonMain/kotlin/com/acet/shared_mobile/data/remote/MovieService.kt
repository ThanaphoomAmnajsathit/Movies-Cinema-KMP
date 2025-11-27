package com.acet.shared_mobile.data.remote

import com.acet.shared_mobile.data.model.ResultWrapper
import com.acet.shared_mobile.data.model.dto.MovieDTO

interface MovieService {
    suspend fun getMovies(): ResultWrapper<List<MovieDTO>?>
}
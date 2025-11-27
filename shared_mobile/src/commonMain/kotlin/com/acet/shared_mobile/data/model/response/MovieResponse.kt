package com.acet.shared_mobile.data.model.response

import com.acet.shared_mobile.client_configuration.DefaultClient.BASE_HOST
import com.acet.shared_mobile.data.model.dto.MovieDTO
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val id: Int?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
) {
    fun toDTO(): MovieDTO {
        return MovieDTO(
            adult = adult,
            backdrop_path = backdrop_path,
            genre_ids = genre_ids,
            id = id,
            original_language = original_language,
            original_title = original_title,
            overview = overview,
            popularity = popularity,
            poster_path = buildString {
                append("https://image.tmdb.org/t/p")
                append("/w500")
                append(poster_path)
            },
            release_date = release_date,
            title = title,
            video = video,
            vote_average = vote_average,
            vote_count = vote_count
        )
    }
}
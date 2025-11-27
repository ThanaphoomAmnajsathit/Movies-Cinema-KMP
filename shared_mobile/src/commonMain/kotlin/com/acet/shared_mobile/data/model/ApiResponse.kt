package com.acet.shared_mobile.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val page: Int?,
    val results: T? = null
)
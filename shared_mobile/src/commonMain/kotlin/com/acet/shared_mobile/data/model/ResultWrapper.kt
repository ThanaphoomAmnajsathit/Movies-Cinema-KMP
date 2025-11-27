package com.acet.shared_mobile.data.model

sealed class ResultWrapper<T>(
    val data: T? = null,
    val error: AppError? = null,
) {
    class Success<T>(
        data: T,
    ) : ResultWrapper<T>(data = data)

    class Error<T>(
        error: AppError? = null,
    ) : ResultWrapper<T>(error = error)
}
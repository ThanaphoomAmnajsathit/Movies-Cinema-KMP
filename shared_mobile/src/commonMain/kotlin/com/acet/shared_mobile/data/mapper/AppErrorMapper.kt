package com.acet.shared_mobile.data.mapper

import com.acet.shared_mobile.data.model.AppError
import com.acet.shared_mobile.data.model.ErrorCode
import com.acet.shared_mobile.data.model.ErrorType
import com.acet.shared_mobile.data.model.LocalizedText
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

object AppErrorMapper {

    private val unknownError = ErrorCode.UnknownError to ErrorType.UNKNOWN_ERROR
    private val internalError = ErrorCode.InternalError to ErrorType.INTERNAL_ERROR
    private val invalidRequest = ErrorCode.InvalidRequest to ErrorType.BAD_REQUEST
    private val authUnauthorized = ErrorCode.AuthUnauthorized to ErrorType.UNAUTHORIZED
    private val advisingConflictError = ErrorCode.AdvisingConflictError to ErrorType.INTERNAL_ERROR

    fun map(throwable: Throwable, content: String? = null): AppError {
        var localized: LocalizedText? = null
        try {
            if (throwable is AppError) return throwable

            val (code, type) = when (throwable) {
                is ResponseException -> {
                    runBlocking {
                        val bodyText = throwable.response.bodyAsText()
                        val bodyObject = Json.decodeFromString<JsonObject?>(bodyText)
                        val code = bodyObject?.get("code")?.toString()
                        val messageObject = bodyObject?.get("message")?.toString()?.let {
                            val rawJson = Json.decodeFromString<String>(it) // remove all slash.
                            Json.decodeFromString<JsonObject?>(rawJson)
                        }
                        val title = messageObject?.get("title")?.toString()?.trim('"')
                        val description = messageObject?.get("description")?.toString()?.trim('"')

                        localized = messageObject?.let {
                            LocalizedText(
                                title = title,
                                message = description
                            )
                        }
                        when (code) {
                            // this is error code from back-end.
                            "AUTH401" -> authUnauthorized
                            "AVS409" -> advisingConflictError
                            else -> unknownError
                        }
                    }
                }

                else -> unknownError
            }

            return AppError(
                code = code,
                type = type,
                content = content,
                localized = localized ?: LocalizedText(
                    title = "Something went wrong.",
                    message = "An error occurred, please try again later."
                ),
                cause = throwable
            )
        } catch (ex: Exception) {
            return AppError(
                code = ErrorCode.UnknownError,
                type = ErrorType.UNKNOWN_ERROR,
                cause = ex,
                localized = localized ?: LocalizedText(
                    title = "Something went wrong.",
                    message = "An error occurred, please try again later."
                )
            )
        }
    }
}
package com.acet.shared_mobile.data.model

data class AppError(
    val code: String,
    val type: ErrorType,
    val content: String? = null,
    val localized: LocalizedText? = null,
    override val cause: Throwable? = null
): RuntimeException(localized?.message, cause)

data class LocalizedText(
    val title: String? = null,
    val message: String? = null
)

enum class ErrorType(val value: String) {
    BAD_REQUEST("BadRequest"),
    INTERNAL_ERROR("InternalError"),
    SERVICE_UNAVAILABLE("ServiceUnavailable"),
    NOT_FOUND("NotFound"),
    FORBIDDEN("Forbidden"),
    UNAUTHORIZED("Unauthorized"),
    UNKNOWN_ERROR("UnknownError");

    fun find(type: String?): ErrorType = when (type) {
        BAD_REQUEST.value -> BAD_REQUEST
        INTERNAL_ERROR.value -> INTERNAL_ERROR
        SERVICE_UNAVAILABLE.value -> SERVICE_UNAVAILABLE
        NOT_FOUND.value -> NOT_FOUND
        FORBIDDEN.value -> FORBIDDEN
        UNAUTHORIZED.value -> UNAUTHORIZED
        else -> UNKNOWN_ERROR
    }
}

object ErrorCode {
    const val UnknownError = "UnknownError"
    const val InternalError = "InternalError"
    const val ServiceUnavailable = "ServiceUnavailable"
    const val TimeOut = "TimeOut"
    const val TooManyRequests = "TooManyRequests"
    const val ValidateError = "ValidateError"
    const val InvalidRequest = "InvalidRequest"
    const val TermNotFound = "TermNotFound"
    const val SignInAccessDenied = "SignInAccessDenied"
    const val TokenParseError = "TokenParseError"
    const val SessionTokenNotFound = "SessionTokenNotFound"
    const val RefreshTokenExpired = "RefreshTokenExpired"
    const val AdvisingConflictError = "AdvisingConflictError"
    const val AuthUnauthorized = "AuthUnauthorized"
}
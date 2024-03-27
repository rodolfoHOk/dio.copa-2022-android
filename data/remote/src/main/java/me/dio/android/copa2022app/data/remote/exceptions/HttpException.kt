package me.dio.android.copa2022app.data.remote.exceptions

sealed class HttpException(
    message: String? = null,
    cause: Throwable? = null
) : Throwable(message, cause)

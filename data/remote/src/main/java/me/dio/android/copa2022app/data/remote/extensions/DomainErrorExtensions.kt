package me.dio.android.copa2022app.data.remote.extensions

import me.dio.android.copa2022app.data.remote.exceptions.NotFoundException
import me.dio.android.copa2022app.data.remote.exceptions.UnexpectedException
import java.net.HttpURLConnection
import retrofit2.HttpException as RetrofitHttpException

internal fun <T> Result<T>.getOrThrowDomainError(): T = getOrElse { throwable ->
    throw throwable.toDomainError()
}

internal fun Throwable.toDomainError(): Throwable {
    return when (this) {
        is RetrofitHttpException -> {
            when (code()) {
                HttpURLConnection.HTTP_NOT_FOUND -> NotFoundException("Ops! Não conseguimos encontrar as partidas :'(")
                else -> UnexpectedException("Ops! Algo ocorreu quando tentamos buscar as partidas :'(")
            }
        }
        else -> this
    }
}

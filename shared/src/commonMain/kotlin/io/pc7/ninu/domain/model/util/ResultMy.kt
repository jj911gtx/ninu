package io.pc7.ninu.domain.model.util

import io.pc7.ninu.data.network.error.Error

sealed class ResultMy<out D, out E: Error> {
    data class Success<out D>(val data: D): ResultMy<D, Nothing>()
    data class Error<out E: io.pc7.ninu.data.network.error.Error>(val error: E):
        ResultMy<Nothing, E>()
}

inline fun <T, E: Error, R> ResultMy<T, E>.map(map: (T) -> R): ResultMy<R, E> {
    return when(this) {
        is ResultMy.Error -> ResultMy.Error(error)
        is ResultMy.Success -> ResultMy.Success(map(data))
    }
}

fun <T, E: Error> ResultMy<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <D, E: Error> ResultMy<D, E>.handle(
    onSuccess: (D) -> Unit,
    onError: (E) -> Unit
) {
    when (this) {
        is ResultMy.Success -> onSuccess(data)
        is ResultMy.Error -> onError(error)
    }
}


typealias EmptyResult<E> = ResultMy<Unit, E>





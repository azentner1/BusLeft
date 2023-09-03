package com.andrej.busleft.base.model

sealed class DataResult<out T> {

    object Loading: DataResult<Nothing>()

    data class Success<out T>(val data: T) : DataResult<T>()

    data class Error(val error: ErrorEntity) : DataResult<Nothing>()
}
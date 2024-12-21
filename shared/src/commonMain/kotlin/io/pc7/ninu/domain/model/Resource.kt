package io.pc7.ninu.domain.model

sealed class Resource<out D> {
    data class Result<out D>(val data: D) : Resource<D>()
    data object Loading : Resource<Nothing>()
}


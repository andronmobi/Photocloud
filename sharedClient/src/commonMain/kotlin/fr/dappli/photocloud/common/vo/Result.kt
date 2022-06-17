package fr.dappli.photocloud.common.vo

sealed class Result {
    object Loading : Result()
    data class Success<T>(val data: T) : Result()
}

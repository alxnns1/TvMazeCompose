package com.alxnns1.tvmazecompose.network

sealed class ResultOf<out T> {
    data class Success<out R>(val value: R): ResultOf<R>()
    object Error: ResultOf<Nothing>()
    object Initial : ResultOf<Nothing>()
}
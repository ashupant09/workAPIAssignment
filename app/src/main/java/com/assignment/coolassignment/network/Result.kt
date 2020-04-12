package com.assignment.coolassignment.network

sealed class Result<out T: Any> {

    data class isLoading(var isLoading: Boolean = false): Result<Nothing>()
    data class onSuccess<out T: Any>(val data: T?, var isLoading: Boolean = false): Result<T>()
    data class onFailure(val data: Any?, var isLoading: Boolean = false): Result<Nothing>()

}
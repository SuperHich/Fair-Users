package com.superhapp.fairusers.data

interface ResultCallback<T> {
    fun onSuccess(data: T?)
    fun onError(error: String?)
}
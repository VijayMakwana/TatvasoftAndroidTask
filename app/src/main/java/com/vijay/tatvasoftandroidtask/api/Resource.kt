package com.vijay.tatvasoftandroidtask.api

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    LOADING_MORE
}

class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> loadingMore(data: T? = null): Resource<T> {
            return Resource(Status.LOADING_MORE, data, null)
        }
    }
}
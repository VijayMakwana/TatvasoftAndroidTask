package com.vijay.tatvasoftandroidtask.api.data

import com.vijay.tatvasoftandroidtask.api.ApiService
import com.vijay.tatvasoftandroidtask.api.model.UserListResponse

class HomeRepo(private val apiService: ApiService) {

    suspend fun getUsers(offset: Int): UserListResponse {
        return apiService.getUsers(offset)
    }
}
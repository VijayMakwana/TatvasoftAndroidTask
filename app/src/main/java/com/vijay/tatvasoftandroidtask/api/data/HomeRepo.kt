package com.vijay.tatvasoftandroidtask.api.data

import com.vijay.tatvasoftandroidtask.api.NetworkHelper
import com.vijay.tatvasoftandroidtask.api.model.UserListResponse

class HomeRepo {

    suspend fun getUsers(offset: Int): UserListResponse {
        return NetworkHelper.getApiService().getUsers(offset)
    }
}
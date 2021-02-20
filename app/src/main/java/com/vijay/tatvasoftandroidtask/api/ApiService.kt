package com.vijay.tatvasoftandroidtask.api

import com.vijay.tatvasoftandroidtask.api.model.UserListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = Const.PAGE_LIMIT,
    ): UserListResponse
}
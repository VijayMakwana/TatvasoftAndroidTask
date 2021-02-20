package com.vijay.tatvasoftandroidtask.api.model

import com.google.gson.annotations.SerializedName

data class UserListResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean
)

data class Data(
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("users")
    val users: List<User>
)

data class User(
    @SerializedName("image")
    val image: String,
    @SerializedName("items")
    val items: List<String>,
    @SerializedName("name")
    val name: String
)
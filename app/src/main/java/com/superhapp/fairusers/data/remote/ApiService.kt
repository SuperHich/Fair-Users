package com.superhapp.fairusers.data.remote

import com.superhapp.fairusers.data.UsersResponse
import com.superhapp.fairusers.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("user?limit=100")
    fun fetchUsers(): Call<UsersResponse>

    @GET("user/{userId}")
    fun fetchUserDetails(@Path("userId") userId: String): Call<User>
}
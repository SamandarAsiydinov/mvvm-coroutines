package com.samsdk.mvvmexample.network

import com.samsdk.mvvmexample.database.entity.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}
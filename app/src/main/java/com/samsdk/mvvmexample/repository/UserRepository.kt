package com.samsdk.mvvmexample.repository

import com.samsdk.mvvmexample.database.UserDatabase
import com.samsdk.mvvmexample.database.entity.User
import com.samsdk.mvvmexample.network.ApiService

class UserRepository(
    private val apiService: ApiService,
    private val db: UserDatabase
) {
    suspend fun getLocalUsers() = db.userDao().getAllUsers()
    suspend fun addUsers(users: List<User>) = db.userDao().addUsers(users)

    suspend fun getRemoteUsers() = apiService.getUsers()
}
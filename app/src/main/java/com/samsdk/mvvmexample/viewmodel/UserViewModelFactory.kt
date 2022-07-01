package com.samsdk.mvvmexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samsdk.mvvmexample.repository.UserRepository
import com.samsdk.mvvmexample.utils.NetworkHelper

class UserViewModelFactory(
    private val userRepository: UserRepository,
    private val networkHelper: NetworkHelper
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository, networkHelper) as T
        }
        throw Exception("Error")
    }
}
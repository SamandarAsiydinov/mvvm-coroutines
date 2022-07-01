package com.samsdk.mvvmexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samsdk.mvvmexample.database.entity.User
import com.samsdk.mvvmexample.repository.UserRepository
import com.samsdk.mvvmexample.utils.NetworkHelper
import com.samsdk.mvvmexample.utils.Resource
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _response: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    val response: LiveData<Resource<List<User>>> get() = _response

    init {
        fetchUsers()
    }
    private fun fetchUsers() {
        viewModelScope.launch {
            _response.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val remoteUsers = userRepository.getRemoteUsers()
                if (remoteUsers.isSuccessful) {
                    userRepository.addUsers(remoteUsers.body() ?: emptyList())
                    _response.postValue(Resource.success(remoteUsers.body()))
                } else {
                    _response.postValue(
                        Resource.error(remoteUsers.errorBody().toString(), null)
                    )
                }
            } else {
                _response.postValue(Resource.success(userRepository.getLocalUsers()))
            }
        }
    }
}
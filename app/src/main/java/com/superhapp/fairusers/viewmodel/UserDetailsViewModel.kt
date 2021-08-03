package com.superhapp.fairusers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superhapp.fairusers.data.ResultCallback
import com.superhapp.fairusers.model.User
import com.superhapp.fairusers.model.UserDetailsRepository
import kotlinx.coroutines.launch

class UserDetailsViewModel constructor(private val repository: UserDetailsRepository)  : ViewModel() {

    private val _userDetails = MutableLiveData<User>()
    val userDetails: LiveData<User> = _userDetails

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    fun fetchUserDetails(userId: String) {
        viewModelScope.launch {
            repository.fetchUserDetails(userId, object : ResultCallback<User> {
                override fun onSuccess(data: User?) {
                    _userDetails.value = data
                }

                override fun onError(error: String?) {
                    _onMessageError.value = error
                }
            })
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.cancel()
    }
}
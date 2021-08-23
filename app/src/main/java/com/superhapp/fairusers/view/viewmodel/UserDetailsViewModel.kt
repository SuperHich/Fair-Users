package com.superhapp.fairusers.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superhapp.fairusers.data.ResultCallback
import com.superhapp.fairusers.data.model.User
import com.superhapp.fairusers.data.repository.UserDetailsRepository
import com.superhapp.fairusers.view.model.toViewModel
import kotlinx.coroutines.launch
import com.superhapp.fairusers.view.model.User as ModelUser

class UserDetailsViewModel constructor(private val repository: UserDetailsRepository)  : ViewModel() {

    private val _userDetails = MutableLiveData<ModelUser>()
    val userDetails: LiveData<ModelUser> = _userDetails

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    fun fetchUserDetails(userId: String) {
        viewModelScope.launch {
            repository.fetchUserDetails(userId, object : ResultCallback<User> {
                override fun onSuccess(data: User?) {
                    _userDetails.value = data?.toViewModel()
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
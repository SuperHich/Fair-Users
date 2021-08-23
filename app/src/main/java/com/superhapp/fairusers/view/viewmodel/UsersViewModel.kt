package com.superhapp.fairusers.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superhapp.fairusers.data.ResultCallback
import com.superhapp.fairusers.data.model.User
import com.superhapp.fairusers.view.model.User as ModelUser
import com.superhapp.fairusers.data.repository.UsersRepository
import com.superhapp.fairusers.view.model.toViewModel
import kotlinx.coroutines.launch

class UsersViewModel constructor(private val repository: UsersRepository)  : ViewModel() {

    private val _users = MutableLiveData<List<ModelUser>>().apply { value = emptyList() }
    val users: LiveData<List<ModelUser>> = _users

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    fun loadUsers() {
        viewModelScope.launch {
            repository.fetchUsers(object : ResultCallback<List<User>> {
                override fun onSuccess(data: List<User>?) {
                    _users.value = data?.map { it.toViewModel() }
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
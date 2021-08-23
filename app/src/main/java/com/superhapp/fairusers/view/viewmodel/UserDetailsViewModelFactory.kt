package com.superhapp.fairusers.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.superhapp.fairusers.data.repository.UserDetailsRepository

class UserDetailsViewModelFactory constructor(private val repository: UserDetailsRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
            UserDetailsViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
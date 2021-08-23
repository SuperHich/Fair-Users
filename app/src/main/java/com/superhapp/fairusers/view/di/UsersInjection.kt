package com.superhapp.fairusers.view.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.superhapp.fairusers.data.remote.ApiClient
import com.superhapp.fairusers.data.remote.UsersRemoteDataSource
import com.superhapp.fairusers.data.datasource.UsersDataSource
import com.superhapp.fairusers.data.repository.UsersRepository
import com.superhapp.fairusers.view.viewmodel.UsersViewModelFactory

object UsersInjection {

    private lateinit var usersDataSource : UsersDataSource
    private lateinit var usersRepository : UsersRepository
    private var userViewModelFactory : UsersViewModelFactory? = null

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        if(userViewModelFactory == null) {
            usersDataSource = UsersRemoteDataSource(ApiClient(context))
            usersRepository = UsersRepository(usersDataSource)
            userViewModelFactory = UsersViewModelFactory(usersRepository)
        }
        return userViewModelFactory!!
    }


}
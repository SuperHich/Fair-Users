package com.superhapp.fairusers.view.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.superhapp.fairusers.data.remote.ApiClient
import com.superhapp.fairusers.data.remote.UserDetailsRemoteDataSource
import com.superhapp.fairusers.data.datasource.UserDetailsDataSource
import com.superhapp.fairusers.data.repository.UserDetailsRepository
import com.superhapp.fairusers.view.viewmodel.UserDetailsViewModelFactory

object UserDetailsInjection {

    private lateinit var userDetailsDataSource : UserDetailsDataSource
    private lateinit var userDetailsRepository : UserDetailsRepository
    private var userDetailsViewModelFactory : UserDetailsViewModelFactory? = null

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        if(userDetailsViewModelFactory == null) {
            userDetailsDataSource = UserDetailsRemoteDataSource(ApiClient(context))
            userDetailsRepository = UserDetailsRepository(userDetailsDataSource)
            userDetailsViewModelFactory = UserDetailsViewModelFactory(userDetailsRepository)
        }
        return userDetailsViewModelFactory!!
    }


}
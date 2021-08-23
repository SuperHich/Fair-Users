package com.superhapp.fairusers.data.repository

import com.superhapp.fairusers.data.ResultCallback
import com.superhapp.fairusers.data.model.User
import com.superhapp.fairusers.data.datasource.UserDetailsDataSource

class UserDetailsRepository(private val userDetailsDataSource: UserDetailsDataSource) {

    fun fetchUserDetails(userId: String, callback: ResultCallback<User>) {
        userDetailsDataSource.fetchUserDetails(userId, callback)
    }

    fun cancel() {
        userDetailsDataSource.cancel()
    }
}

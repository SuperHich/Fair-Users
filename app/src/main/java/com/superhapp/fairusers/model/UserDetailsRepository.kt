package com.superhapp.fairusers.model

import com.superhapp.fairusers.data.ResultCallback

class UserDetailsRepository(private val userDetailsDataSource: UserDetailsDataSource) {

    fun fetchUserDetails(userId: String, callback: ResultCallback<User>) {
        userDetailsDataSource.fetchUserDetails(userId, callback)
    }

    fun cancel() {
        userDetailsDataSource.cancel()
    }
}

package com.superhapp.fairusers.model

import com.superhapp.fairusers.data.ResultCallback

interface UserDetailsDataSource {
    fun fetchUserDetails(userId: String, callback: ResultCallback<User>)
    fun cancel()
}
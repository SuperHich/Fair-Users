package com.superhapp.fairusers.data.datasource

import com.superhapp.fairusers.data.ResultCallback
import com.superhapp.fairusers.data.model.User

interface UserDetailsDataSource {
    fun fetchUserDetails(userId: String, callback: ResultCallback<User>)
    fun cancel()
}
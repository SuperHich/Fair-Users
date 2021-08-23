package com.superhapp.fairusers.data.datasource

import com.superhapp.fairusers.data.ResultCallback
import com.superhapp.fairusers.data.model.User

interface UsersDataSource {
    fun fetchUsers(callback: ResultCallback<List<User>>)
    fun cancel()
}
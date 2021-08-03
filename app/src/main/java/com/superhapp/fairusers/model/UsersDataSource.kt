package com.superhapp.fairusers.model

import com.superhapp.fairusers.data.ResultCallback

interface UsersDataSource {
    fun fetchUsers(callback: ResultCallback<List<User>>)
    fun cancel()
}
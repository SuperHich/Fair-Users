package com.superhapp.fairusers.data.repository

import com.superhapp.fairusers.data.ResultCallback
import com.superhapp.fairusers.data.model.User
import com.superhapp.fairusers.data.datasource.UsersDataSource

class UsersRepository(private val usersDataSource: UsersDataSource) {

    fun fetchUsers(callback: ResultCallback<List<User>>) {
        usersDataSource.fetchUsers(callback)
    }

    fun cancel() {
        usersDataSource.cancel()
    }
}

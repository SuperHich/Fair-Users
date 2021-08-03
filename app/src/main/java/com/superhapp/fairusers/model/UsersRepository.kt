package com.superhapp.fairusers.model

import com.superhapp.fairusers.data.ResultCallback

class UsersRepository(private val usersDataSource: UsersDataSource) {

    fun fetchUsers(callback: ResultCallback<List<User>>) {
        usersDataSource.fetchUsers(callback)
    }

    fun cancel() {
        usersDataSource.cancel()
    }
}

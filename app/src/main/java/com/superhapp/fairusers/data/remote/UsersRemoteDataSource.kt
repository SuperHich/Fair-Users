package com.superhapp.fairusers.data.remote

import com.superhapp.fairusers.data.ResultCallback
import com.superhapp.fairusers.data.model.UsersResponse
import com.superhapp.fairusers.data.model.User
import com.superhapp.fairusers.data.datasource.UsersDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRemoteDataSource(apiClient: ApiClient) : UsersDataSource {

    private var call: Call<UsersResponse>? = null
    private val service = apiClient.build()

    override fun fetchUsers(callback: ResultCallback<List<User>>) {
        call = service.fetchUsers()
        call?.enqueue(object : Callback<UsersResponse> {
            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<UsersResponse>,
                response: Response<UsersResponse>
            ) {
                response.body()?.let {
                    if (response.isSuccessful && (it.data.isNotEmpty())) {
                        callback.onSuccess(it.data)
                    } else {
                        callback.onError(response.errorBody()?.string())
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.cancel()
    }
}
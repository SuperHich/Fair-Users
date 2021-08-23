package com.superhapp.fairusers.data.remote

import com.superhapp.fairusers.data.ResultCallback
import com.superhapp.fairusers.data.model.User
import com.superhapp.fairusers.data.datasource.UserDetailsDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailsRemoteDataSource(apiClient: ApiClient) : UserDetailsDataSource {

    private var call: Call<User>? = null
    private val service = apiClient.build()

    override fun fetchUserDetails(userId: String, callback: ResultCallback<User>) {
        call = service.fetchUserDetails(userId)
        call?.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it)
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
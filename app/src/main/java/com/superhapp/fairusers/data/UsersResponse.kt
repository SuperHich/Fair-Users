package com.superhapp.fairusers.data

import com.superhapp.fairusers.model.User
import java.io.Serializable

data class UsersResponse(
    val data: List<User>,
    val total: Int,
    val page: Int,
    val limit: Int,
    val offset: Int
) : Serializable

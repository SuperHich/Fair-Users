package com.superhapp.fairusers.view.model

import java.io.Serializable
import java.util.*

data class User(
    val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val picture: String,
    val dateOfBirth: Date?,
    val gender: String?,
    val location: Location?,
    val phone: String?
) : Serializable
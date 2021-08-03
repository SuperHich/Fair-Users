package com.superhapp.fairusers.model

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
    val phone: String?,
    val registerDate: Date?,
    val updatedAt: Date?
) : Serializable

data class Location(
    val city: String,
    val country: String,
    val state: String,
    val street: String,
    val timezone: String
) : Serializable

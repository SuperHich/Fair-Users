package com.superhapp.fairusers.data.model

import java.io.Serializable

data class Location(
    val city: String,
    val country: String,
    val state: String,
    val street: String,
    val timezone: String
) : Serializable

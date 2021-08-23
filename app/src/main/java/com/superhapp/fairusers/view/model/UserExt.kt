package com.superhapp.fairusers.view.model

import com.superhapp.fairusers.data.model.Location as ModelLocation
import com.superhapp.fairusers.data.model.User as ModelUser

fun ModelUser.toViewModel() : User {

    return User(
        this.id,
        this.title,
        this.firstName,
        this.lastName,
        this.email,
        this.picture,
        this.dateOfBirth,
        this.gender,
        this.location?.toViewModel(),
        this.phone
    )
}

fun ModelLocation.toViewModel() : Location {
    return Location(
        this.city,
        country,
        state,
        street,
        timezone
    )
}
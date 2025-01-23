package com.fraggeil.ticketator.data.mappers

import com.fraggeil.ticketator.data.database.ProfileEntity
import com.fraggeil.ticketator.domain.model.Profile

fun ProfileEntity.toProfile(): Profile {
    return Profile(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        middleName = this.middleName,
        phoneNumber = this.phoneNumber,
        uid = this.uid
    )
}

fun Profile.toProfileEntity(): ProfileEntity {
    return ProfileEntity(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        middleName = this.middleName,
        phoneNumber = this.phoneNumber,
        uid = this.uid
    )
}
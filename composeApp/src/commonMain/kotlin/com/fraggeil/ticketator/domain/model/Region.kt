package com.fraggeil.ticketator.domain.model

import com.fraggeil.ticketator.domain.model.District

data class Region(
    val id: Int,
    val name: String,
    val districts: List<District>
)

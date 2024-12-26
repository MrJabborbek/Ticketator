package com.fraggeil.ticketator.domain.model

data class Filter(
    val from: District? = null,
    val to: District? = null,
    val dateGo: Long? = null,
    val dateBack: Long? = null,
    val type: FilterType = FilterType.ONE_WAY,
)

enum class FilterType{
    ONE_WAY,
    ROUND_TRIP
}

package com.fraggeil.ticketator.domain.model

import com.fraggeil.ticketator.domain.model.uzbekistan.District
import com.fraggeil.ticketator.domain.model.uzbekistan.Region

data class Filter(
    val fromRegion: Region? = null,
    val toRegion: Region? = null,
    val fromDistrict: District? = null,
    val toDistrict: District? = null,
    val dateGo: Long? = null,
    val dateBack: Long? = null,
    val type: FilterType = FilterType.ONE_WAY,
)

enum class FilterType{
    ONE_WAY,
    ROUND_TRIP
}

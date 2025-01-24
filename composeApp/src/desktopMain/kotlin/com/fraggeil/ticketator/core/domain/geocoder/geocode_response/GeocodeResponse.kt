package com.fraggeil.ticketator.core.domain.geocoder.geocode_response

import dev.jordond.compass.InternalCompassApi
import dev.jordond.compass.geocoder.web.google.internal.ResultResponse
import dev.jordond.compass.geocoder.web.google.internal.StatusResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GeocodeResponse @OptIn(InternalCompassApi::class) constructor(
    @SerialName("results")
    public val results: List<ResultResponse> = emptyList(),

//    @SerialName("status")
//    public val status: StatusResponse? = null,

    @SerialName("error_message")
    public val errorMessage: String? = null,
)
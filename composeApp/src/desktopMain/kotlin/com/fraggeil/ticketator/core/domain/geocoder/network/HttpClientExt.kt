package com.fraggeil.ticketator.core.domain.geocoder.network

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): T? {
    val response = try {
        execute()
    } catch(e: SocketTimeoutException) {
        return null
    } catch(e: UnresolvedAddressException) {
        return null
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return null
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): T? {
    return when(response.status.value) {
        in 200..299 -> {
            try {
           response.body<T>()
            } catch(e: NoTransformationFoundException) {
                null
            }
        }
        408 -> null
        429 -> null
        in 500..599 -> null
        else -> null
    }
}
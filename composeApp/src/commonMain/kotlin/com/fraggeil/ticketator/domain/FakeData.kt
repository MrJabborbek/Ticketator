package com.fraggeil.ticketator.domain

import com.fraggeil.ticketator.core.domain.DateTimeUtil
import com.fraggeil.ticketator.core.domain.toFormattedDate
import com.fraggeil.ticketator.domain.model.District
import com.fraggeil.ticketator.domain.model.Journey
import com.fraggeil.ticketator.domain.model.Post
import com.fraggeil.ticketator.domain.model.Region
import kotlin.random.Random

object FakeData{
    val posts = listOf(
        Post(
            id = 1,
            title = "Some title 1",
            imageUrl = "https://cdn.asaxiy.uz/asaxiy-content/uploads/banner/desktop/674355c3a2e00.jpeg.webp",
            body = "Some description 1",
            date = Random.nextLong(from = 1732080685, until = 1732680685)
                .toFormattedDate(hoursEnabled = true)
        ),
        Post(
            id = 2,
            title = "Some title 2",
            imageUrl = "https://cdn.asaxiy.uz/asaxiy-content/uploads/banner/desktop/673d700e2fb37.jpg.webp",
            body = "Some description 2",
            date = Random.nextLong(from = 1732080685, until = 1732680685)
                .toFormattedDate(hoursEnabled = true)
        ),
        Post(
            id = 3,
            title = "Some title 3",
            imageUrl = "https://cdn.asaxiy.uz/asaxiy-content/uploads/banner/desktop/664c688f64418.jpg.webp",
            body = "Some description 3 Some description 3 Some description 3 Some description 3 Some description 3Some description 3 Some description 3Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3  Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3  Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3  Some description 3",
            date = Random.nextLong(from = 1732080685, until = 1732680685)
                .toFormattedDate(hoursEnabled = true)
        )
    )
    val regions = (1..15).map {
        Region(
            id = it,
            name = "Region$it",
            districts = (1..13).map { num ->
                District(
                    id = it * 10 + num,
                    "District $num of R$it",
                    abbr = "DI$num"

                )
            }
        )
    }

    val fakePosts = listOf(
        Post(
            id = 1,
            title = "Some title 1",
            imageUrl = "https://cdn.asaxiy.uz/asaxiy-content/uploads/banner/desktop/674355c3a2e00.jpeg.webp",
            body = "Some description 1",
            date = Random.nextLong(from = 1732080685, until = 1732680685)
                .toFormattedDate(hoursEnabled = true)
        ),
        Post(
            id = 2,
            title = "Some title 2",
            imageUrl = "https://cdn.asaxiy.uz/asaxiy-content/uploads/banner/desktop/673d700e2fb37.jpg.webp",
            body = "Some description 2",
            date = Random.nextLong(from = 1732080685, until = 1732680685)
                .toFormattedDate(hoursEnabled = true)
        ),
        Post(
            id = 3,
            title = "Some title 3",
            imageUrl = "https://cdn.asaxiy.uz/asaxiy-content/uploads/banner/desktop/664c688f64418.jpg.webp",
            body = "Some description 3 Some description 3 Some description 3 Some description 3 Some description 3Some description 3 Some description 3Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3  Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3  Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3  Some description 3",
            date = Random.nextLong(from = 1732080685, until = 1732680685)
                .toFormattedDate(hoursEnabled = true)
        )
    )

    val fakeJourneys = (1..12).map {
        val fromRegion = regions.random()
        val fromDistrict = fromRegion.districts.random()
        val toRegion = regions.random()
        val toDistrict = toRegion.districts.random()
        val timeStart = DateTimeUtil.nowMilliseconds() + it*1000*60*60
        val timeArrival = timeStart + 8*1000*60*60 // 8 hours

        Journey(
            id = it.toString(),
            timeStart = timeStart,
            timeArrival = timeArrival,
            from = Pair(fromRegion, fromDistrict),
            to = Pair(toRegion, toDistrict),
            price = Random.nextInt(from = 100, until = 1000),
            seats = 55,
            seatsReserved = listOf(1,2,3,4),
            seatsAvailable = (5..45).map { it },
            seatsUnavailable = (46..55).map { it },
            stopAt = emptyList()
        )
    }
}
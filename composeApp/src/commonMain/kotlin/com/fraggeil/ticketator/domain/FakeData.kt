package com.fraggeil.ticketator.domain

import com.fraggeil.ticketator.core.domain.toFormattedDate
import com.fraggeil.ticketator.domain.model.District
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
            name = "Region name is will be $it",
            districts = (1..13).map { num ->
                District(
                    id = it * 10 + num,
                    "District $num of region $it"
                )
            }
        )
    }
}
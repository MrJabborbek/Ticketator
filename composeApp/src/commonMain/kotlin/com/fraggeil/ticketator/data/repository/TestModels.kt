package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.DateTimeUtil
import com.fraggeil.ticketator.core.domain.toFormattedDate
import com.fraggeil.ticketator.domain.model.CardData
import com.fraggeil.ticketator.domain.model.uzbekistan.District
import com.fraggeil.ticketator.domain.model.Journey
import com.fraggeil.ticketator.domain.model.Passenger
import com.fraggeil.ticketator.domain.model.Post
import com.fraggeil.ticketator.domain.model.Profile
import com.fraggeil.ticketator.domain.model.uzbekistan.Region
import com.fraggeil.ticketator.domain.model.Ticket
import com.fraggeil.ticketator.domain.model.User
import com.fraggeil.ticketator.domain.model.uzbekistan.Uzbekistan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.random.Random

val testPosts = listOf(

    Post(
        id = 2,
        title = "Some title 2",
//        imageUrl = "https://cdn.asaxiy.uz/asaxiy-content/uploads/banner/desktop/673d700e2fb37.jpg.webp",
        imageUrl = "https://d94tn94v8mvlz.cloudfront.net/blog/wp-content/uploads/2016/12/Use_your_app_not_to_miss_your_bus-1024x410.png",
        body = "Some description 2",
        date = Random.nextLong(from = 1732080685, until = 1732680685).toFormattedDate(hoursEnabled = true)
    ),
    Post(
        id = 3,
        title = "Some title 3",
//        imageUrl = "https://cdn.asaxiy.uz/asaxiy-content/uploads/banner/desktop/664c688f64418.jpg.webp",
        imageUrl = "https://d33v4339jhl8k0.cloudfront.net/docs/assets/58e5529add8c8e5c5731278e/images/62d6ecd32967a40ec4ce6ce7/file-vbMggH3ICi.png",
        body = "Some description 3 Some description 3 Some description 3 Some description 3 Some description 3Some description 3 Some description 3Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3  Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3  Some description 3 Some description 3 Some description 3 Some description 3 Some description 3 Some description 3  Some description 3",
        date = Random.nextLong(from = 1732080685, until = 1732680685).toFormattedDate(hoursEnabled = true)
    ),
    Post(
        id = 1,
        title = "Some title 1",
//        imageUrl = "https://cdn.asaxiy.uz/asaxiy-content/uploads/banner/desktop/674355c3a2e00.jpeg.webp",
        imageUrl = "https://storage.kun.uz/source/8/zh5RBpch-jHMYyAP2XR9gG6hB74AeWEx.jpg",
        body = "Some description 1",
        date = Random.nextLong(from = 1732080685, until = 1732680685).toFormattedDate(hoursEnabled = true)
    ),

)
val _testPosts = MutableStateFlow(testPosts)


//val regions = (1..15).map {
//    Region(
//        id = it,
//        name = "Region$it",
//        districts = (1..13).map { num ->
//            District(
//                id = it * 10 + num,
//                "District $num of R$it",
//                abbr = "DI$num"
//
//            )
//        }
//    )
//}

//val regions = Uzbekistan.uzbekistan
val regions = Regions.avtoticketuz_regions


val testUsers = (1..5).map {
    User(
        id = it,
        name = "User $it",
        phoneNumber = "+998$it$it$it$it$it$it",
    )
}

//val _testCurrentProfile = MutableStateFlow<Profile?>(null)

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
        price = 139000,
        seats = 55,
        seatsReserved = listOf(1,2,3,4),
        seatsAvailable = (5..45).map { it },
        seatsUnavailable = (46..55).map { it },
        stopAt = emptyList()
    )
}
val fakeTickets = (1..3).map {
    val journey = fakeJourneys.random()
    Ticket(
        ticketId = it.toLong(),
        journey = journey,
        buyTime = DateTimeUtil.nowMilliseconds(),
        passenger = Passenger("Passenger $it", seat = journey.seatsAvailable.random(), phone = "+99890$it$it$it$it$it$it$it" ),
        qrCodeLinkOrToken = "https://medium.com/mobile-innovation-network/qrkit-qrcode-generator-in-compose-multiplatform-for-android-and-ios-858ec3e1e2b2"
    )
}

//val _testNewTickets = MutableStateFlow<List<Ticket>>(fakeTickets)

val  _testJourneys = MutableStateFlow<List<Journey>>(emptyList())

val _testSendDataToCheck = MutableStateFlow<List<Triple<Journey, CardData, String>>>(emptyList()) // journey, (card num. valid), token







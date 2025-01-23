package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.domain.model.uzbekistan.District
import com.fraggeil.ticketator.domain.model.uzbekistan.Region

object Regions {
    val toshkentShahri = Region(
        id = 1,
        name = "Toshkent shahri",
        districts = listOf(
            District(id = 1, name = "Toshkent", abbr = "TSK")
        )
    )

    val toshkentViloyatiRegion = Region(
        id = 2,
        name = "Toshkent viloyati",
        districts = listOf(
            District(id = 1, name = "Chirchiq", abbr = "CHR"),
            District(id = 2, name = "Angren", abbr = "ANG"),
        )
    )

    val buxoroRegion = Region(
        id = 3,
        name = "Buxoro viloyati",
        districts = listOf(
            District(id = 1, name = "Gazli", abbr = "GZL"),
            District(id = 2, name = "G'ijduvon", abbr = "GIJ"),
            District(id = 3, name = "Buxoro", abbr = "BXR"),
            District(id = 4, name = "Qorako'l", abbr = "QRK")
        )
    )

    val qoraqalpogistonRegion = Region(
        id = 4,
        name = "Qoraqalpog‘iston Respublikasi",
        districts = listOf(
            District(id = 1, name = "Beruniy", abbr = "BRN"),
            District(id = 2, name = "Amudaryo", abbr = "AMD"),
            District(id = 3, name = "Nukus", abbr = "NKS"),
            District(id = 4, name = "To‘rtko‘l", abbr = "TRK")
        )
    )

    val navoiyRegion = Region(
        id = 5,
        name = "Navoiy viloyati",
        districts = listOf(
            District(id = 1, name = "Nurota", abbr = "NRT"),
            District(id = 2, name = "Qiziltepa", abbr = "QZT"),
            District(id = 3, name = "Navoiy", abbr = "NVI"),
            District(id = 4, name = "Chuya", abbr = "CHY"),
            District(id = 5, name = "Xatirchi", abbr = "XTR"),
            District(id = 6, name = "Uchquduq", abbr = "UCH"),
            District(id = 7, name = "Zarafshon", abbr = "ZRF")
        )
    )

    val samarqandRegion = Region(
        id = 6,
        name = "Samarqand viloyati",
        districts = listOf(
            District(id = 1, name = "Qo‘shrobod", abbr = "QRB"),
            District(id = 2, name = "Chelak", abbr = "CHL"),
            District(id = 3, name = "Samarqand", abbr = "SMQ"),
            District(id = 4, name = "Mitan", abbr = "MTN"),
            District(id = 5, name = "Payariq", abbr = "PYA"),
            District(id = 6, name = "Loish", abbr = "LSH"),
            District(id = 7, name = "Ishtixon", abbr = "ISH"),
            District(id = 8, name = "Mirbozor", abbr = "MBZ"),
            District(id = 9, name = "Kattaqo‘rg‘on", abbr = "KQR"),
            District(id = 10, name = "Zarmitan", abbr = "ZRM"),
            District(id = 11, name = "Jo‘sh", abbr = "JSH"),
            District(id = 12, name = "Jom", abbr = "JOM"),
            District(id = 13, name = "Sariko‘l", abbr = "SRK"),
            District(id = 14, name = "Bulung‘ur", abbr = "BUL")
        )
    )

    val surxondaryoRegion = Region(
        id = 7,
        name = "Surxondaryo viloyati",
        districts = listOf(
            District(id = 1, name = "Dehqonobod", abbr = "DHQ"),
            District(id = 2, name = "Qumqorg‘on", abbr = "QMQ"),
            District(id = 3, name = "Sariosiyo", abbr = "SRS"),
            District(id = 4, name = "Jarqo‘rg‘on", abbr = "JRQ"),
            District(id = 5, name = "Sherobod", abbr = "SHR"),
            District(id = 6, name = "Sho‘rchi", abbr = "SHR"),
            District(id = 7, name = "Angor", abbr = "ANG"),
            District(id = 8, name = "Termiz", abbr = "TMZ"),
            District(id = 9, name = "Darband", abbr = "DRB"),
            District(id = 10, name = "Uzun", abbr = "UZN"),
            District(id = 11, name = "Denov", abbr = "DNV")
        )
    )

    val jizzaxRegion = Region(
        id = 8,
        name = "Jizzax viloyati",
        districts = listOf(
            District(id = 1, name = "Zomin", abbr = "ZMN"),
            District(id = 2, name = "Dashtobod", abbr = "DTB"),
            District(id = 3, name = "G‘allaorol", abbr = "GLO"),
            District(id = 4, name = "Jizzax", abbr = "JZX")
        )
    )

    val qashqadaryoRegion = Region(
        id = 9,
        name = "Qashqadaryo viloyati",
        districts = listOf(
            District(id = 1, name = "Chiroqchi", abbr = "CHR"),
            District(id = 2, name = "Qarshi", abbr = "QAR"),
            District(id = 3, name = "Ettitom", abbr = "ETM"),
            District(id = 4, name = "G‘uzor", abbr = "GZR"),
            District(id = 5, name = "Chim", abbr = "CHM"),
            District(id = 6, name = "Shaxrisabz", abbr = "SHX")
        )
    )

    val sirdaryoRegion = Region(
        id = 10,
        name = "Sirdaryo viloyati",
        districts = listOf(
            District(id = 1, name = "Xovos", abbr = "XOV"),
            District(id = 2, name = "Yangiyer", abbr = "YNY"),
            District(id = 3, name = "Guliston", abbr = "GLS")
        )
    )

    val xorazmRegion = Region(
        id = 11,
        name = "Xorazm viloyati",
        districts = listOf(
            District(id = 1, name = "Hazorasp", abbr = "HZR"),
            District(id = 2, name = "Xonqa", abbr = "XNQ"),
            District(id = 3, name = "Xiva", abbr = "XVA"),
            District(id = 4, name = "Bog‘ot", abbr = "BGT"),
            District(id = 5, name = "Urganch", abbr = "URG")
        )
    )

    val xalqaroReys = Region(
        id = 12,
        name = "Xalqaro reys",
        districts = listOf(
            District(id = 1, name = "Astana", abbr = "AST"),
            District(id = 2, name = "Ryazan", abbr = "RYA"),
            District(id = 3, name = "Naberezhnye Chelny", abbr = "NCH"),
            District(id = 4, name = "Olma-ota", abbr = "OLM"),
            District(id = 5, name = "Samara", abbr = "SAM"),
            District(id = 6, name = "Vladimir", abbr = "VLA"),
            District(id = 7, name = "Karaganda", abbr = "KAR"),
            District(id = 8, name = "Nijniy Novgorod", abbr = "NIG"),
            District(id = 9, name = "Tolyatti", abbr = "TOL"),
            District(id = 10, name = "Novosibirsk", abbr = "NOV"),
            District(id = 11, name = "Perm", abbr = "PER"),
            District(id = 12, name = "Cheboksary", abbr = "CHE"),
            District(id = 13, name = "Almetyevsk", abbr = "ALM"),
            District(id = 14, name = "Qozon", abbr = "QOZ"),
            District(id = 15, name = "Moskva", abbr = "MOS"),
            District(id = 16, name = "Nijnekamsk", abbr = "NJK"),
            District(id = 17, name = "Ufa", abbr = "UFA"),
            District(id = 18, name = "Bishkek", abbr = "BIS"),
            District(id = 19, name = "Penza", abbr = "PEN"),
            District(id = 20, name = "Kolomna", abbr = "KOL"),
            District(id = 21, name = "Orenburg", abbr = "ORE")
        )
    )

    val avtoticketuz_regions = listOf(
        toshkentShahri,
        toshkentViloyatiRegion,
        buxoroRegion,
        qoraqalpogistonRegion,
        navoiyRegion,
        samarqandRegion,
        surxondaryoRegion,
        jizzaxRegion,
        qashqadaryoRegion,
        sirdaryoRegion,
        xorazmRegion,
        xalqaroReys
    )
}
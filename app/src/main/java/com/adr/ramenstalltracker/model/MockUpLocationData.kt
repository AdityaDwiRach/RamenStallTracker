package com.adr.ramenstalltracker.model

class MockUpLocationData {
    data class LatLong(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0
    )

    val listData = listOf(
        LatLong(-6.224284, 106.860456),
        LatLong(-6.222684, 106.862516),
        LatLong(-6.225606, 106.867784),
        LatLong(-6.221809, 106.869983),
        LatLong(-6.217116, 106.865348),
        LatLong(-6.212156, 106.859533),
        LatLong(-6.206271, 106.869462),
        LatLong(-6.203685, 106.875380),
        LatLong(-6.204325, 106.882279),
        LatLong(-6.205573, 106.887268)
    )
}
package com.adr.ramenstalltracker.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class MockUpLocationData: IMockUpLocationData {

    @Parcelize
    data class StallData(
        val stallName: String = "",
        val latitude: Double = 0.0,
        val longitude: Double = 0.0
    ): Parcelable

    private var listData = arrayListOf(
        StallData("Ramen 1",-6.224284, 106.860456),
        StallData("Ramen 2",-6.222684, 106.862516),
        StallData("Ramen 3",-6.225606, 106.867784),
        StallData("Ramen 4",-6.221809, 106.869983),
        StallData("Ramen 5",-6.217116, 106.865348),
        StallData("Ramen 6",-6.212156, 106.859533),
        StallData("Ramen 7",-6.206271, 106.869462),
        StallData("Ramen 8",-6.203685, 106.875380),
        StallData("Ramen 9",-6.204325, 106.882279),
        StallData("Ramen 10",-6.205573, 106.887268)
    )

    override fun getListMockupData(): ArrayList<StallData> {
        return listData
    }

    override fun setListMockupData(listData: ArrayList<StallData>) {
        this.listData = listData
    }
}
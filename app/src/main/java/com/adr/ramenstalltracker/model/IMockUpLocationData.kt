package com.adr.ramenstalltracker.model

interface IMockUpLocationData {
    fun getListMockupData(): ArrayList<MockUpLocationData.StallData>
    fun setListMockupData(listData: ArrayList<MockUpLocationData.StallData>)
}
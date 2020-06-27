package com.adr.ramenstalltracker.adapter

import com.adr.ramenstalltracker.model.MockUpLocationData

interface IRVAdapterMainModel {
    fun setListData(listData: ArrayList<MockUpLocationData.StallData>)
    fun getListData(): ArrayList<MockUpLocationData.StallData>
    fun getListDataSize(listData: ArrayList<MockUpLocationData.StallData>): Int
    fun removeFromList(position: Int)
}

interface IRVAdapterMainView {
    fun refreshData()
    fun onItemClicked(position: Int)
}
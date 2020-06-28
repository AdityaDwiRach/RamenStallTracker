package com.adr.ramenstalltracker.adapter

import android.content.Context
import com.adr.ramenstalltracker.model.MockUpLocationData

interface IRVAdapterMainModel {
    fun setListData(listData: ArrayList<MockUpLocationData.StallData>)
    fun getListData(): ArrayList<MockUpLocationData.StallData>
    fun getListDataSize(listData: ArrayList<MockUpLocationData.StallData>): Int
    fun removeFromList(position: Int)
    fun onSaveMockupData(context: Context, listData: ArrayList<MockUpLocationData.StallData>)
}

interface IRVAdapterMainView {
    fun refreshData()
    fun onItemClicked(position: Int)
}
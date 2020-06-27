package com.adr.ramenstalltracker.view

import com.adr.ramenstalltracker.model.MockUpLocationData

interface IMainActivityView {
    fun onUpdateListData(listData: ArrayList<MockUpLocationData.StallData>)
    fun onSaveListSuccess()
    fun onGetListSuccess(listData: ArrayList<MockUpLocationData.StallData>)
    fun onSaveListFailed(error: String)
    fun onGetListFailed(error: String)
}
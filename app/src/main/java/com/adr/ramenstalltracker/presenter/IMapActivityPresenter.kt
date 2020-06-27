package com.adr.ramenstalltracker.presenter

import android.content.Context
import com.adr.ramenstalltracker.model.MockUpLocationData

interface IMapActivityPresenter {
    fun onSaveMockupData(context: Context, listData: ArrayList<MockUpLocationData.StallData>)
    fun onGetMockupData(context: Context)
}
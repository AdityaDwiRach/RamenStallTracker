package com.adr.ramenstalltracker.presenter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.adr.ramenstalltracker.model.MockUpLocationData
import com.adr.ramenstalltracker.view.IMapActivityView
import com.adr.ramenstalltracker.view.MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MapActivityPresenter(private val iMapActivityView: IMapActivityView): IMapActivityPresenter {
    override fun onSaveMockupData(
        context: Context,
        listData: ArrayList<MockUpLocationData.StallData>
    ) {
        val shared = context.getSharedPreferences(MainActivity.SHARED_DATA, AppCompatActivity.MODE_PRIVATE)
        val editor = shared.edit()
        val gson = Gson()
        val json = gson.toJson(listData)
        editor.putString(MainActivity.MOCKUP_DATA, json)
        editor.apply()

        if (shared.getString(MainActivity.MOCKUP_DATA, "").isNullOrEmpty()){
            iMapActivityView.onSaveListFailed("Data failed to save.")
        } else {
            iMapActivityView.onSaveListSuccess()
        }
    }

    override fun onGetMockupData(context: Context) {
        val shared = context.getSharedPreferences(MainActivity.SHARED_DATA, AppCompatActivity.MODE_PRIVATE)
        val gson = Gson()
        val json = shared.getString(MainActivity.MOCKUP_DATA, null)
        val type: Type = object : TypeToken<ArrayList<MockUpLocationData.StallData?>?>() {}.type

        val listData: ArrayList<MockUpLocationData.StallData> = gson.fromJson(json, type)
        if (listData.isNullOrEmpty()){
            iMapActivityView.onGetListFailed("Failed to get data")
        } else {
            iMapActivityView.onGetListSuccess(listData)
        }
    }
}
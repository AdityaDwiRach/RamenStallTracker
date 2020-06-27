package com.adr.ramenstalltracker.presenter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.adr.ramenstalltracker.model.MockUpLocationData
import com.adr.ramenstalltracker.view.IMainActivityView
import com.adr.ramenstalltracker.view.MainActivity
import com.adr.ramenstalltracker.view.MainActivity.Companion.MOCKUP_DATA
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MainActivityPresenter(private val iMainActivityView: IMainActivityView): IMainActivityPresenter {
    override fun onSaveMockupData(
        context: Context,
        listData: ArrayList<MockUpLocationData.StallData>
    ) {
        val shared = context.getSharedPreferences(MainActivity.SHARED_DATA, AppCompatActivity.MODE_PRIVATE)
        val editor = shared.edit()
        val gson = Gson()
        val json = gson.toJson(listData)
        editor.putString(MOCKUP_DATA, json)
        editor.apply()

        if (shared.getString(MOCKUP_DATA, "").isNullOrEmpty()){
            iMainActivityView.onSaveListFailed("Data failed to save.")
        } else {
            iMainActivityView.onSaveListSuccess()
        }
    }

    override fun onGetMockupData(context: Context) {
        val shared = context.getSharedPreferences(MainActivity.SHARED_DATA, AppCompatActivity.MODE_PRIVATE)
        val gson = Gson()
        val json = shared.getString(MOCKUP_DATA, null)
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type

        val listData: ArrayList<MockUpLocationData.StallData> = gson.fromJson(json, type)
        if (listData.isNullOrEmpty()){
            iMainActivityView.onGetListFailed("Failed to get data")
        } else {
            iMainActivityView.onGetListSuccess(listData)
        }
    }
}
package com.adr.ramenstalltracker.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adr.ramenstalltracker.R
import com.adr.ramenstalltracker.model.MockUpLocationData
import com.adr.ramenstalltracker.view.MainActivity.Companion.EXTRA_DATA
import com.adr.ramenstalltracker.view.MapActivity
import kotlinx.android.synthetic.main.item_rv_main.view.*

class RVAdapterMain: RecyclerView.Adapter<RVAdapterMain.ViewHolder>(), IRVAdapterMainModel, IRVAdapterMainView {

    private var listData: ArrayList<MockUpLocationData.StallData> = ArrayList()
    private var context: Context? = null

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ramenName: TextView = itemView.tv_ramen_stall_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_main, parent, false))
    }

    override fun getItemCount(): Int = getListDataSize(this.listData)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bindListData = getListData()

        holder.ramenName.text = bindListData[position].stallName

        holder.itemView.setOnClickListener {
            onItemClicked(position)
        }

    }

    override fun setListData(listData: ArrayList<MockUpLocationData.StallData>) {
        this.listData = listData
    }

    override fun getListData(): ArrayList<MockUpLocationData.StallData> {
        return this.listData
    }

    override fun getListDataSize(listData: ArrayList<MockUpLocationData.StallData>): Int {
        return listData.size
    }

    override fun removeFromList(position: Int) {
        listData.removeAt(position)
        notifyDataSetChanged()
    }

    override fun refreshData() {
        notifyDataSetChanged()
    }

    override fun onItemClicked(position: Int) {
        val intent = Intent(context, MapActivity::class.java)
        intent.putExtra(EXTRA_DATA, listData[position])
        context?.startActivity(intent)
    }

}
package com.adr.ramenstalltracker.view

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adr.ramenstalltracker.R
import com.adr.ramenstalltracker.adapter.RVAdapterMain
import com.adr.ramenstalltracker.model.MockUpLocationData
import com.adr.ramenstalltracker.presenter.MainActivityPresenter
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class MainActivity : AppCompatActivity(), IMainActivityView {

    companion object {
        const val EXTRA_DATA = "extradata"
        const val SHARED_DATA = "shareddata"
        const val MOCKUP_DATA = "mockupdata"
    }

    private var listData: ArrayList<MockUpLocationData.StallData> = ArrayList()
    private val presenter by lazy { MainActivityPresenter(this) }
    private val rvAdapter by lazy { RVAdapterMain() }
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onGetMockupData(this)

        recyclerView = findViewById(R.id.rv_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        rvAdapter.setListData(listData)
        rvAdapter.refreshData()
        recyclerView.adapter = rvAdapter

        val itemTouchHelper = object : SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                rvAdapter.removeFromList(viewHolder.adapterPosition)
                Toast.makeText(applicationContext, "Item Deleted", Toast.LENGTH_SHORT).show()
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView,
                                     viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
                                     actionState: Int, isCurrentlyActive: Boolean)
            { RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState,
                    isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(this@MainActivity,
                            R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState,
                    isCurrentlyActive)
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> startActivity(Intent(this, MapActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onUpdateListData(listData: ArrayList<MockUpLocationData.StallData>) {
        rvAdapter.setListData(listData)
        rvAdapter.refreshData()
    }

    override fun onSaveListSuccess() {
        Toast.makeText(this, "Data has been saved.", Toast.LENGTH_SHORT).show()
    }

    override fun onGetListSuccess(listData: ArrayList<MockUpLocationData.StallData>) {
        this.listData = listData
    }

    override fun onSaveListFailed(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun onGetListFailed(error: String) {
        this.listData = ArrayList()
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}
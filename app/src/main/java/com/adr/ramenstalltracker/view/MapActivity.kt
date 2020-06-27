package com.adr.ramenstalltracker.view

import android.location.Geocoder
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.adr.ramenstalltracker.R
import com.adr.ramenstalltracker.model.MockUpLocationData
import com.adr.ramenstalltracker.presenter.MapActivityPresenter
import com.adr.ramenstalltracker.view.MainActivity.Companion.EXTRA_DATA
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback, IMapActivityView {

    private var listData: ArrayList<MockUpLocationData.StallData> = ArrayList()
    private val presenter by lazy { MapActivityPresenter(this) }
    private lateinit var mMap: GoogleMap
    private var stallData: MockUpLocationData.StallData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        presenter.onGetMockupData(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (intent.extras != null){
            stallData = intent.getParcelableExtra(EXTRA_DATA)
            val stallName = stallData!!.stallName
            val stallLatLng = LatLng(stallData?.latitude!!, stallData?.longitude!!)
            mMap.addMarker(MarkerOptions().position(stallLatLng).title(stallName))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(stallLatLng))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f))
        } else {
            val indonesiaLatLng = LatLng(-2.272836, 114.184949)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(indonesiaLatLng))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(4.5f))
            mMap.setOnMapLongClickListener {

                val markerOptions = MarkerOptions()
                markerOptions.position(it)

                val newMarker = mMap.addMarker(markerOptions)

                val dialog = AlertDialog.Builder(this)
                val view = layoutInflater.inflate(R.layout.alert_dialog_custom, null)
                val info = view.findViewById<TextView>(R.id.tv_alert_lat_lng)
                val newName = view.findViewById<EditText>(R.id.et_alert_save)
                info.text = getAddress(it.latitude, it.longitude)
                dialog.setView(view)
                dialog.setTitle("Save Your Ramen Stall")
                dialog.setCancelable(true)
                dialog.setPositiveButton("SAVE"
                ) { dialogInter, _ ->
                    newMarker.remove()
                    val name = newName.text.toString()
                    listData.add(MockUpLocationData.StallData(name, it.latitude, it.longitude))
                    presenter.onSaveMockupData(this, listData)
                    dialogInter.dismiss()
                }
                dialog.setNegativeButton("CANCEL"
                ) { dialogInter, _ ->
                    newMarker.remove()
                    dialogInter.dismiss()
                }
                dialog.create().show()

            }
        }
    }

    private fun getAddress(latitude: Double, longitude: Double): String {
        val address = Geocoder(this).getFromLocation(latitude, longitude, 1)
        return address[0].getAddressLine(0)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
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
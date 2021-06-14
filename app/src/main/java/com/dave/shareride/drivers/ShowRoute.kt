package com.dave.shareride.drivers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dave.shareride.R
import com.dave.shareride.adapter.RoutesHistoryAdapter
import com.dave.shareride.network_requests.calls.RetrofitCallsAccount
import kotlinx.android.synthetic.main.activity_show_route.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShowRoute : AppCompatActivity() {

    private var retrofitCalls: RetrofitCallsAccount = RetrofitCallsAccount()
    private lateinit var recyclerView : RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_route)

        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        GlobalScope.launch(Dispatchers.IO){
            val routeList =  retrofitCalls.getMyRoutes(this@ShowRoute)
            CoroutineScope(Dispatchers.Main).launch {
                val routesHistoryAdapter = RoutesHistoryAdapter(routeList, this@ShowRoute)
                recyclerView.adapter = routesHistoryAdapter
            }

        }

        btnCreateRoute.setOnClickListener {

            val intent = Intent(this, CreatePickUps::class.java)
            startActivity(intent)

        }

//        val mReceive: MapEventsReceiver = object : MapEventsReceiver {
//            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
//                // write your code here
//                return false
//            }
//
//            override fun longPressHelper(p: GeoPoint): Boolean {
//
//                createPoint(p)
//                // write your code here
//                return false
//            }
//        }
//        val overlayEvents = MapEventsOverlay(this, mReceive)
//        map.overlays.add(overlayEvents)

    }

    override fun onStart() {
        super.onStart()

//        initMap()

    }



//    private fun initMap() {
//
//
//        var latitude = "-1.292066"
//        var longitude = "36.821945"
//
//        val geoPoint = GeoPoint(latitude.toDouble(), longitude.toDouble())
//
//        map.setTileSource(TileSourceFactory.OpenTopo)
//        map.setMultiTouchControls(true)
//        map.controller.setZoom(4.0)
//        map.setMaxZoomLevel(null)
//
//        val mapController = map.controller as MapController
//        mapController.setZoom(18.5)
//        mapController.setCenter(geoPoint)
//
//        //        mapController.animateTo(geoPoint, 16.5, 9000L);
//        val overlayItem = OverlayItem("Lat", "Long", geoPoint)
//        val markerDrawable = this.resources.getDrawable(R.drawable.ic_action_pin)
//        overlayItem.setMarker(markerDrawable)
//        val overlayItemArrayList = ArrayList<OverlayItem>()
//        overlayItemArrayList.add(overlayItem)
//        val locationOverlay: ItemizedOverlay<OverlayItem> = ItemizedIconOverlay(this,
//            overlayItemArrayList, null)
//        map.overlays.add(locationOverlay)
//
//        val marker = Marker(map)
//        marker.icon = markerDrawable
//        marker.position = geoPoint
//        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
////        marker.title = coordinateDetails
//        marker.setPanToView(true)
//        marker.isDraggable = true
//        map.overlays.add(marker)
//        map.invalidate()
//
//    }

//    private fun createPoint(geoPoint: GeoPoint) {
//
//        map.setTileSource(TileSourceFactory.OpenTopo)
//        map.setMultiTouchControls(true)
//        map.controller.setZoom(4.0)
//        map.setMaxZoomLevel(null)
//
//        val mapController = map.controller as MapController
//        mapController.setZoom(18.5)
//        mapController.setCenter(geoPoint)
//
//        val overlayItem = OverlayItem("Lat", "Long", geoPoint)
//        val markerDrawable = resources.getDrawable(R.drawable.pin)
//        overlayItem.setMarker(markerDrawable)
//        val overlayItemArrayList = ArrayList<OverlayItem>()
//        overlayItemArrayList.add(overlayItem)
//        val locationOverlay: ItemizedOverlay<OverlayItem> = ItemizedIconOverlay(this@ShowRoute,
//            overlayItemArrayList, null)
//        map.overlays.add(locationOverlay)
//
//        //Get the latitudes and longitudes
//        val latitudes = geoPoint.latitude
//        val longitudes = geoPoint.longitude
//
//        //Create a dialog
//
//
//    }
}
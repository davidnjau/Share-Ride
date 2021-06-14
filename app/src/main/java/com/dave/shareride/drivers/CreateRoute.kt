package com.dave.shareride.drivers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dave.shareride.R
import kotlinx.android.synthetic.main.activity_create_route.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.overlay.*
import java.util.ArrayList

class CreateRoute : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_route)

        val mReceive: MapEventsReceiver = object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                // write your code here
                return false
            }

            override fun longPressHelper(p: GeoPoint): Boolean {

                createPoint(p)
                // write your code here
                return false
            }
        }
        val overlayEvents = MapEventsOverlay(this, mReceive)
        map.overlays.add(overlayEvents)

    }

    override fun onStart() {
        super.onStart()

        initMap()

    }

    private fun initMap() {


        var latitude = "-1.292066"
        var longitude = "36.821945"

        val geoPoint = GeoPoint(latitude.toDouble(), longitude.toDouble())

        map.setTileSource(TileSourceFactory.OpenTopo)
        map.setMultiTouchControls(true)
        map.controller.setZoom(4.0)
        map.setMaxZoomLevel(null)

        val mapController = map.controller as MapController
        mapController.setZoom(18.5)
        mapController.setCenter(geoPoint)

        //        mapController.animateTo(geoPoint, 16.5, 9000L);
        val overlayItem = OverlayItem("Lat", "Long", geoPoint)
        val markerDrawable = this.resources.getDrawable(R.drawable.ic_action_pin)
        overlayItem.setMarker(markerDrawable)
        val overlayItemArrayList = ArrayList<OverlayItem>()
        overlayItemArrayList.add(overlayItem)
        val locationOverlay: ItemizedOverlay<OverlayItem> = ItemizedIconOverlay(this,
            overlayItemArrayList, null)
        map.overlays.add(locationOverlay)

        val marker = Marker(map)
        marker.icon = markerDrawable
        marker.position = geoPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//        marker.title = coordinateDetails
        marker.setPanToView(true)
        marker.isDraggable = true
        map.overlays.add(marker)
        map.invalidate()

    }

    private fun createPoint(geoPoint: GeoPoint) {

        map.setTileSource(TileSourceFactory.OpenTopo)
        map.setMultiTouchControls(true)
        map.controller.setZoom(4.0)
        map.setMaxZoomLevel(null)

        val mapController = map.controller as MapController
        mapController.setZoom(18.5)
        mapController.setCenter(geoPoint)

        val overlayItem = OverlayItem("Lat", "Long", geoPoint)
        val markerDrawable = resources.getDrawable(R.drawable.pin)
        overlayItem.setMarker(markerDrawable)
        val overlayItemArrayList = ArrayList<OverlayItem>()
        overlayItemArrayList.add(overlayItem)
        val locationOverlay: ItemizedOverlay<OverlayItem> = ItemizedIconOverlay(this@CreateRoute,
            overlayItemArrayList, null)
        map.overlays.add(locationOverlay)

        //Get the latitudes and longitudes
        val latitudes = geoPoint.latitude
        val longitudes = geoPoint.longitude

        //Create a dialog




    }
}
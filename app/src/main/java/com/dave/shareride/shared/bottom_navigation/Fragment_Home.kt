package com.dave.shareride.shared.bottom_navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.dave.shareride.R
import com.dave.shareride.drivers.CreateRoute
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.*
import java.util.*


class Fragment_Home : Fragment() {

    private lateinit var map : MapView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        map = rootView.findViewById(R.id.map)

        startMap()

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
        val overlayEvents = MapEventsOverlay(requireActivity(), mReceive)
        map.overlays.add(overlayEvents)

        rootView.findViewById<Button>(R.id.btnCreateRide).setOnClickListener {

            val intent = Intent(requireActivity(), CreateRoute::class.java)
            startActivity(intent)

        }

        return rootView
    }



    private fun createPoint(geoPoint: GeoPoint) {

        val overlayItem = OverlayItem("Lat", "Long", geoPoint)
        val markerDrawable = this.resources.getDrawable(R.drawable.pin)
        overlayItem.setMarker(markerDrawable)
        val overlayItemArrayList = ArrayList<OverlayItem>()
        overlayItemArrayList.add(overlayItem)
        val locationOverlay: ItemizedOverlay<OverlayItem> = ItemizedIconOverlay(requireActivity(),
            overlayItemArrayList, null)
        map.overlays.add(locationOverlay)

    }

    private fun startMap() {

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
        val locationOverlay: ItemizedOverlay<OverlayItem> = ItemizedIconOverlay(requireActivity(),
            overlayItemArrayList, null)
        map.overlays.add(locationOverlay)

//        val coordinateDetails = """
//            Parcel Details: ${tvReferenceno.text}
//            latitude: $latitude
//            longitude: $longitude
//            """.trimIndent()

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


}
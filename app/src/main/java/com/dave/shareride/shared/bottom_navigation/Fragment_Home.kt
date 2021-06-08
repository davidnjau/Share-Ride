package com.dave.shareride.shared.bottom_navigation

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dave.shareride.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem
import java.util.ArrayList


class Fragment_Home : Fragment() {

    private lateinit var map : MapView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        map = rootView.findViewById(R.id.map)

        startMap()


        return rootView
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
package com.tellirium.farmer.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.tellirium.farmer.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_farm_map.*

@AndroidEntryPoint
class FarmMapFragment : Fragment(R.layout.fragment_farm_map) , OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private var map: GoogleMap? = null

    var polygon : Polygon? = null

    val latLngList = mutableListOf<LatLng>()
    val markerList = mutableListOf<Marker>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(){
            map = it
        }

        btnDrawPolygon.setOnClickListener {
            polygon?.remove()
            val polyOptions = PolygonOptions().addAll(latLngList).clickable(true)
            polygon = map?.addPolygon(
                polyOptions
            )
            polygon?.strokeColor = Color.RED
            polygon?.fillColor = Color.CYAN
        }

        btnClear.setOnClickListener {
            polygon?.remove()
            markerList.forEach { it.remove() }
            latLngList.clear()
            markerList.clear()
        }

    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
        map?.uiSettings?.isZoomControlsEnabled = true
        map?.setOnMarkerClickListener(this)
        map?.setOnMapClickListener {
            val markerOptions = MarkerOptions().position(it)
            val marker = map?.addMarker(markerOptions)
            latLngList.add(it)
            marker?.let { it1 -> markerList.add(it1) }
        }
    }

    override fun onMarkerClick(p0: Marker?) = true

}